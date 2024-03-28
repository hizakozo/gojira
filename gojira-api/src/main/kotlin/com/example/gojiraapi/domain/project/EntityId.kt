package com.example.gojiraapi.domain.project

import java.util.UUID

@JvmInline
value class EntityId<T>(private val value: UUID) {
    override fun toString(): String = value.toString()
    companion object {
        fun <T>random() = EntityId<T>(UUID.randomUUID())
        fun <T>fromString(value: String) = EntityId<T>(UUID.fromString(value))
    }
}