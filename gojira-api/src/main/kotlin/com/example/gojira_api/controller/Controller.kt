package com.example.gojira_api.controller

import com.example.gojira_api.domain.user.User
import kotlinx.coroutines.reactor.awaitSingleOrNull
import org.springframework.security.core.context.ReactiveSecurityContextHolder
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.bodyValueAndAwait
import java.util.UUID

interface Controller {

    suspend fun withAuthenticatedUser(
        block: suspend (user: User) -> ServerResponse
    ): ServerResponse {
        val jwt = ReactiveSecurityContextHolder.getContext()
            .awaitSingleOrNull()
            ?.authentication?.principal as? Jwt
        if (jwt == null) {
            return ServerResponse.status(401).bodyValueAndAwait("Unauthorized")
        }
        val userId = jwt.subject.let(UUID::fromString)
        val email = jwt.getClaim<String>("email")
        val name = jwt.getClaim<String>("name")
        val externalUserId = jwt.getClaim<String>("externalUserId")

        val user = User.reconstruct(
            userId = userId,
            email = email,
            name = name,
            externalUserId = externalUserId
        )
        return block(user)
    }
}