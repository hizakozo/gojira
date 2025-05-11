package com.example.gojira_api.controller

import com.example.gojira_api.controller.gen.api.ITicketsController
import com.example.gojira_api.controller.gen.model.TicketRequest
import com.example.gojira_api.controller.gen.model.TicketResponse
import com.example.gojira_api.usecase.TicketOutput
import com.example.gojira_api.usecase.TicketUseCase
import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.bodyValueAndAwait
import org.springframework.web.reactive.function.server.bodyToMono
import java.util.*

@Component
class TicketController(
    private val ticketUseCase: TicketUseCase
): ITicketsController, Controller {
    override suspend fun postTicket(request: ServerRequest): ServerResponse =
        withAuthenticatedUser { user ->
            request.bodyToMono<TicketRequest>().awaitSingle().let { reqBody ->
                ticketUseCase.createTicket(
                    projectId = reqBody.projectId,
                    title = reqBody.title,
                    content = reqBody.content
                ).fold(
                    {
                        ServerResponse.badRequest().bodyValueAndAwait(it.toResponse())
                    },
                    {
                        ServerResponse.ok().bodyValueAndAwait(
                            it.toResponse()
                        )
                    }
                )
            }
        }

    override suspend fun getTicketTicketId(request: ServerRequest): ServerResponse =
        withAuthenticatedUser { user ->
            ticketUseCase.getTicketById(
                ticketId = UUID.fromString(request.pathVariable("ticketId"))
            ).fold(
                {
                    ServerResponse.badRequest().bodyValueAndAwait(it.toResponse())
                },
                {
                    ServerResponse.ok().bodyValueAndAwait(
                        it.toResponse()
                    )
                }
            )
        }

    override suspend fun putTicketTicketId(request: ServerRequest): ServerResponse =
        withAuthenticatedUser { user ->
            request.bodyToMono<TicketRequest>().awaitSingle().let { reqBody ->
                ticketUseCase.updateTicket(
                    ticketId = UUID.fromString(request.pathVariable("ticketId")),
                    title = reqBody.title,
                    content = reqBody.content
                ).fold(
                    {
                        ServerResponse.badRequest().bodyValueAndAwait(it.toResponse())
                    },
                    {
                        ServerResponse.ok().bodyValueAndAwait(
                            it.toResponse()
                        )
                    }
                )
            }
        }

    override suspend fun deleteTicketTicketId(request: ServerRequest): ServerResponse =
        withAuthenticatedUser { user ->
            ticketUseCase.deleteTicket(
                ticketId = UUID.fromString(request.pathVariable("ticketId"))
            ).fold(
                {
                    ServerResponse.badRequest().bodyValueAndAwait(it.toResponse())
                },
                {
                    ServerResponse.ok().bodyValueAndAwait(
                        mapOf("success" to it)
                    )
                }
            )
        }

    private fun TicketOutput.toResponse() =
        TicketResponse(
            ticketId = ticketId,
            projectId = projectId,
            title = title,
            content = content
        )
}
