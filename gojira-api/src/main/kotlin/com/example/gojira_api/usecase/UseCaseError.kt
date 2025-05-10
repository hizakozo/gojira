package com.example.gojira_api.usecase

import com.example.gojira_api.error.Error
import com.example.gojira_api.error.ErrorCode

class UseCaseError(
    override val message: String,
    override val code: ErrorCode,
    override val throwable: Throwable? = null
): Error