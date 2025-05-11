package com.example.gojira_api.controller

import com.example.gojira_api.controller.gen.api.ISigninController
import com.example.gojira_api.envioroment.JwtConfig
import com.example.gojira_api.usecase.UserUseCase
import kotlinx.coroutines.reactor.awaitSingleOrNull
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.context.ReactiveSecurityContextHolder
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.bodyValueAndAwait

@Component
class SigninController(
    private val userUseCase: UserUseCase,
    private val jwtConfig: JwtConfig
): ISigninController {
    @Value("\${spring.security.oauth2.resourceserver.jwt.name-space}")
    private lateinit var nameSpace: String

    override suspend fun signIn(request: ServerRequest): ServerResponse {
        val jwt = ReactiveSecurityContextHolder.getContext()
            .awaitSingleOrNull()
            ?.authentication?.principal as? Jwt

        if (jwt == null) {
            println("JWT is null")
            return ServerResponse.status(401).bodyValueAndAwait("Unauthorized")
        }
        val externalUserId = jwt.subject
        val email = jwt.getClaim<String>("${nameSpace}email")
        val name = jwt.getClaim<String>("${nameSpace}name")

        return userUseCase.signIn(
            externalUserId = externalUserId,
            email = email,
            name = name
        ).fold(
            {
                ServerResponse.badRequest().bodyValueAndAwait(it.toResponse())
            },
            { user ->
                val token = jwtConfig.generateToken(
                    userId = user.userId.value.toString(),
                    email = user.email.value
                )
                ServerResponse.ok().bodyValueAndAwait(Response(token))
            }
        )
    }
    data class Response(val token: String)
}