package com.example.gojira_api.error

enum class ErrorCode(code: String) {
    NOT_FOUND("001"),
    INVALID_REQUEST("002"),
    UNAUTHORIZED("003"),
    FORBIDDEN("004"),
    INTERNAL_SERVER_ERROR("005"),
    BAD_REQUEST("006"),
    CONFLICT("007"),
}