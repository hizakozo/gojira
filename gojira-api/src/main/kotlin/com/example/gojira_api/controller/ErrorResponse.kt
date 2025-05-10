package com.example.gojira_api.controller

import com.example.gojira_api.usecase.UseCaseError

data class ErrorResponse(
    val message: String,
    val code: String,
)

fun UseCaseError.toResponse(): ErrorResponse {
    return ErrorResponse(
        message = this.message,
        code = this.code.name,
    )
}