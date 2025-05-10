package com.example.gojira_api.controller.gen.api

import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.server.coRouter

interface ITicketsController {
        suspend fun deleteTicketTicketId(request: ServerRequest): ServerResponse
        suspend fun getTicketTicketId(request: ServerRequest): ServerResponse
        suspend fun postTicket(request: ServerRequest): ServerResponse
        suspend fun putTicketTicketId(request: ServerRequest): ServerResponse
}

@Configuration
class TicketsControllerRouter {
@Bean
fun ticketsControllerRoutes(controller: ITicketsController) = coRouter {
        DELETE("/tickets/{ticketId}", controller::deleteTicketTicketId)
        GET("/tickets/{ticketId}", controller::getTicketTicketId)
        POST("/tickets", controller::postTicket)
        PUT("/tickets/{ticketId}", controller::putTicketTicketId)
    }
}