package com.example.gojira_api.envioroment

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.ReactiveAuthenticationManager
import org.springframework.security.oauth2.jwt.NimbusReactiveJwtDecoder
import org.springframework.security.oauth2.server.resource.authentication.JwtReactiveAuthenticationManager
import java.util.Date
import javax.crypto.SecretKey
import javax.crypto.spec.SecretKeySpec

@Configuration
@ConfigurationProperties(prefix = "jwt")
class JwtConfig {
    lateinit var secret: String
    var expiration: Long = 0
    val key: SecretKey get() =
        SecretKeySpec(secret.toByteArray(), SignatureAlgorithm.HS256.jcaName)

    fun generateToken(userId: String, email: String): String {
        val now = Date()
        val expiryDate = Date(now.time + expiration * 1000)

        return Jwts.builder()
            .setSubject(userId)
            .claim("email", email)
            .setIssuedAt(now)
            .setExpiration(expiryDate)
            .signWith(key, SignatureAlgorithm.HS256)
            .compact()
    }
}

@Configuration
class ReactiveAuthenticationManagerConfig(
    private val jwtConfig: JwtConfig,
    @Value("\${spring.security.oauth2.resourceserver.jwt.jwk-set-uri}") private val jwkSetUri: String
) {
    fun auth0AuthenticationManager(): ReactiveAuthenticationManager {
        val jwtDecoder = NimbusReactiveJwtDecoder.withJwkSetUri(jwkSetUri).build()
        return JwtReactiveAuthenticationManager(jwtDecoder)
    }

    @Bean
    fun appAuthenticationManager(): ReactiveAuthenticationManager {
        val jwtDecoder = NimbusReactiveJwtDecoder.withSecretKey(jwtConfig.key)
            .build()
        return JwtReactiveAuthenticationManager(jwtDecoder)
    }
}