package com.example.gojiraapi.domain.project

import com.example.gojiraapi.GojiraError

interface DomainError : GojiraError

interface ValueObjectError : DomainError {
    val name: String
    val input: String
    val reason: String
    override val message: String
        get() = "Invalid $name: $input, $reason"
}