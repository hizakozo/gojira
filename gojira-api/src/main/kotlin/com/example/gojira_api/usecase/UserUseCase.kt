package com.example.gojira_api.usecase

import arrow.core.Either
import arrow.core.raise.either
import com.example.gojira_api.domain.user.ExternalUserId
import com.example.gojira_api.domain.user.User
import com.example.gojira_api.domain.user.UserRepository
import com.example.gojira_api.error.ErrorCode
import org.springframework.stereotype.Component

@Component
class UserUseCase(
    private val userRepository: UserRepository,
) {
    suspend fun signIn(
        externalUserId: String,
        email: String,
        name: String
    ): Either<UseCaseError, User> = either {
        userRepository.findByExternalId(
            externalUserId = ExternalUserId.reconstruct(externalUserId)
        ) ?: run {
            User.create(
                externalUserId = externalUserId,
                email = email,
                name = name
            ).map { user ->
                userRepository.create(user)
            }
                .mapLeft { error ->
                    UseCaseError(
                        message = error.message,
                        code = ErrorCode.INVALID_REQUEST
                    )
                }
                .bind()
        }
    }
}