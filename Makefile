.PHONY: codegen

# Generate code from OpenAPI specification
setup:
	cd gojira-api && \
	./gradlew update generatePrimaryJooq openApiGenerate
