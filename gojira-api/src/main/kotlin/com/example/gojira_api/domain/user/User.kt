package com.example.gojira_api.domain.user

import arrow.core.Either
import arrow.core.raise.either
import arrow.core.raise.ensure
import com.example.gojira_api.domain.DomainError
import java.util.UUID

class User private constructor(
    val userId: UserId,
    val externalUserId: ExternalUserId,
    val email: UserEmail,
    val name: UserName
) {
    companion object {
        fun create(
            externalUserId: String,
            email: String,
            name: String
        ): Either<DomainError, User> = either {
            val userExternalId = ExternalUserId.create(externalUserId).bind()
            val userEmail = UserEmail.create(email).bind()
            val userName = UserName.create(name).bind()
            User(
                userId = UserId.create(),
                externalUserId = userExternalId,
                email = userEmail,
                name = userName
            )
        }

        fun reconstruct(
            userId: UUID,
            externalUserId: String,
            email: String,
            name: String
        ): User {
            return User(
                userId = UserId.reconstruct(userId),
                externalUserId = ExternalUserId.reconstruct(externalUserId),
                email = UserEmail.reconstruct(email),
                name = UserName.reconstruct(name)
            )
        }
    }
}

class UserId private constructor(
    val value: UUID
) {
    companion object {
        fun create(): UserId {
            return UserId(UUID.randomUUID())
        }

        fun reconstruct(value: UUID): UserId {
            return UserId(value)
        }
    }
}

class ExternalUserId private constructor(
    val value: String
) {
    companion object {
        fun create(value: String): Either<DomainError, ExternalUserId> = either {
            ensure(value.isNotBlank()) {
                DomainError("External user ID cannot be blank")
            }
            ensure(value.length <= 255) {
                DomainError("External user ID cannot be longer than 255 characters")
            }
            ExternalUserId(value)
        }

        fun reconstruct(value: String): ExternalUserId {
            return ExternalUserId(value)
        }
    }
}

class UserEmail private constructor(
    val value: String
) {
    companion object {
        fun create(value: String): Either<DomainError, UserEmail> = either {
            ensure(value.isNotBlank()) {
                DomainError("User email cannot be blank")
            }
            ensure(value.length <= 255) {
                DomainError("User email cannot be longer than 255 characters")
            }
            // Simple email validation
            ensure(value.matches(Regex("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$"))) {
                DomainError("Invalid email format")
            }
            UserEmail(value)
        }

        fun reconstruct(value: String): UserEmail {
            return UserEmail(value)
        }
    }
}

class UserName private constructor(
    val value: String
) {
    companion object {
        fun create(value: String): Either<DomainError, UserName> = either {
            ensure(value.isNotBlank()) {
                DomainError("User name cannot be blank")
            }
            ensure(value.length <= 255) {
                DomainError("User name cannot be longer than 255 characters")
            }
            UserName(value)
        }

        fun reconstruct(value: String): UserName {
            return UserName(value)
        }
    }
}
