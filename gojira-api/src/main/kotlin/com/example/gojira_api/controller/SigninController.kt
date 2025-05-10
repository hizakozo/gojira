package com.example.gojira_api.controller

import com.example.gojira_api.controller.gen.api.ISigninController
import kotlinx.coroutines.reactor.awaitSingleOrNull
import org.springframework.security.core.context.ReactiveSecurityContextHolder
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.bodyValueAndAwait
import org.springframework.web.reactive.function.server.buildAndAwait

@Component
class SigninController: ISigninController {
    override suspend fun signIn(request: ServerRequest): ServerResponse {
        val jwt = ReactiveSecurityContextHolder.getContext()
            .awaitSingleOrNull()
            ?.authentication?.principal as? Jwt

        if (jwt == null) {
            println("JWT is null")
            return ServerResponse.status(401).bodyValueAndAwait("Unauthorized")
        }
        println("JWT: $jwt")
        println("User ID: ${jwt.subject}")
        println("Email: ${jwt.getClaim<String>("https://gojira-app.example.com/email")}")
        println("Name: ${jwt.getClaim<String>("https://gojira-app.example.com/name")}")

        return ServerResponse.ok().buildAndAwait()
    }
}