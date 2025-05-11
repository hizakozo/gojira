plugins {
	kotlin("jvm") version "2.1.0"
	kotlin("plugin.spring") version "2.1.0"
	id("org.springframework.boot") version "3.4.5"
	id("io.spring.dependency-management") version "1.1.7"
	id("org.liquibase.gradle") version "2.2.0"
	id("org.openapi.generator") version "7.13.0"
	id("nu.studer.jooq") version "9.0"
}

group = "com.example"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(17)
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-security")
	implementation("org.springframework.boot:spring-boot-starter-oauth2-resource-server")
	implementation("org.springframework.boot:spring-boot-starter-webflux")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
	implementation("org.postgresql:postgresql")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("io.projectreactor:reactor-test")
	testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
	testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test")
	testImplementation("org.springframework.security:spring-security-test")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")

	//liquibase
	implementation("org.liquibase:liquibase-core:4.19.0")
	implementation("org.liquibase:liquibase-groovy-dsl:3.0.2")
	liquibaseRuntime("org.postgresql:postgresql:42.3.1")
	liquibaseRuntime("org.liquibase:liquibase-core:4.19.0")
	liquibaseRuntime("org.liquibase:liquibase-groovy-dsl:3.0.2")
	liquibaseRuntime("info.picocli:picocli:4.6.1")
	testImplementation("org.postgresql:postgresql")

	implementation("io.arrow-kt:arrow-core:2.1.0")
	implementation("io.arrow-kt:arrow-fx-coroutines:2.1.0")

	// JOOQ Core with Spring Boot
	implementation("org.springframework.boot:spring-boot-starter-jooq")
	implementation("org.jooq:jooq:3.19.16")
	implementation("org.jooq:jooq-kotlin-coroutines:3.19.16")

	// JOOQ Code Generator Dependencies
	jooqGenerator("org.jooq:jooq:3.19.16")
	jooqGenerator("org.jooq:jooq-meta:3.19.16")
	jooqGenerator("org.jooq:jooq-codegen:3.19.16")
	jooqGenerator("org.postgresql:postgresql:42.7.2")
	jooqGenerator("jakarta.xml.bind:jakarta.xml.bind-api:4.0.2")

	// jwt
	implementation("io.jsonwebtoken:jjwt-api:0.11.5")                        // JWT (生成と検証)
	implementation("io.jsonwebtoken:jjwt-impl:0.11.5")                       // JWT 実装
	implementation("io.jsonwebtoken:jjwt-jackson:0.11.5")                    // JSON パース (JWT 解析)
}

jooq {
	edition.set(nu.studer.gradle.jooq.JooqEdition.OSS)
	configurations {
		create("Primary") {
			generateSchemaSourceOnCompilation.set(true)
			jooqConfiguration.apply {
				jdbc.apply {
					driver = "org.postgresql.Driver"
					url = "jdbc:postgresql://${System.getenv("DB_URL") ?: "localhost:5432/gojira"}"
					user = System.getenv("DB_USER") ?: "docker"
					password = System.getenv("DB_PASS") ?: "docker"
				}
				generator.apply {
					name = "org.jooq.codegen.KotlinGenerator"
					database.apply {
						inputSchema = "public"
					}
					target.apply {
						packageName = "com.example.gojira_api.driver.gen"
						directory = "${project.projectDir}/src/main/kotlin/com/example/gojira_api/driver/gen"
					}
				}
			}
		}
	}
}
liquibase {
	activities.register("main") {
		val db_url = System.getenv("DB_URL") ?: "localhost:5432/gojira"
		val db_user = System.getenv("DB_USER") ?: "docker"
		val db_password = System.getenv("DB_PASS") ?: "docker"

		this.arguments = mapOf(
			"logLevel" to "info",
			"changeLogFile" to "src/main/resources/liquibase/xml/db.changelog.xml",
			"url" to "jdbc:postgresql://$db_url",
			"username" to db_user,
			"password" to db_password,
		)
	}
	runList = "main"
}
openApiGenerate {
	generatorName.set("kotlin-spring")
	inputSpec.set("$rootDir/../open-api/openapi.yaml")

	outputDir.set("$rootDir")
	globalProperties.set(
		mapOf(
			"models" to "",
			"apis" to "",
			"supportingFiles" to "true"
		)
	)

	additionalProperties.set(
		mapOf(
			"modelPackage" to "com.example.gojira_api.controller.gen.model",
			"apiPackage" to "com.example.gojira_api.controller.gen.api",
			"reactive" to "true",
			"useBeanValidation" to "false",
			"documentationProvider" to "none",
			"interfaceOnly" to "true",
			"apiSuffix" to "Controller",

		)
	)

	templateDir.set("$rootDir/../open-api/templates/kotlin-spring")
}

kotlin {
	compilerOptions {
		freeCompilerArgs.addAll("-Xjsr305=strict")
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}

tasks.withType<JavaExec> {
}

tasks.register<JavaExec>("generateEntities") {
	group = "codegen"
	description = "Generate Entities.kt from database schema"

	classpath = sourceSets["main"].runtimeClasspath

	systemProperty("dbHost", System.getenv("DB_MASTER_HOST") ?: "localhost")
	systemProperty("dbPort", System.getenv("DB_MASTER_PORT") ?: "5432")
	systemProperty("dbUser", System.getenv("DB_USER") ?: "docker")
	systemProperty("dbPass", System.getenv("DB_PASS") ?: "docker")
	systemProperty("dbName", System.getenv("DB_NAME") ?: "gojira")
	systemProperty("outputDirectory", "${project.rootDir}/src/main/kotlin/com/example/gojira_api/driver/gen/")
	systemProperty("fileName", "Entities.kt")
	systemProperty("packageName", "com.example.gojira_api.driver.gen")
	systemProperty("driverType", "PostgreSQL")
	systemProperty("ignoreTables", "databasechangelog,databasechangeloglock")

	mainClass.set("com.example.gojira_api.driver.GenerateEntities")
}