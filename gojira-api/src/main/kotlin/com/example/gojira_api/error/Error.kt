package com.example.gojira_api.error

interface Error {
    val message: String
    val code: ErrorCode
    val throwable: Throwable?
}