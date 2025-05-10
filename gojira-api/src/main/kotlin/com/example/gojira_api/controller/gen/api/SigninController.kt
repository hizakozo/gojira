package com.example.gojira_api.controller.gen.api

import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.server.coRouter

interface ISigninController {
        suspend fun signIn(request: ServerRequest): ServerResponse
}

@Configuration
class SigninControllerRouter {
@Bean
fun signinControllerRoutes(controller: ISigninController) = coRouter {
        POST("/signin", controller::signIn)
    }
}