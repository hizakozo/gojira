package com.example.gojira_api.controller

import com.example.gojira_api.controller.gen.api.IProjectsController
import com.example.gojira_api.controller.gen.model.ProjectRequest
import com.example.gojira_api.controller.gen.model.ProjectResponse
import com.example.gojira_api.controller.gen.model.TicketResponse
import com.example.gojira_api.usecase.ProjectOutput
import com.example.gojira_api.usecase.ProjectUseCase
import com.example.gojira_api.usecase.TicketOutput
import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.bodyValueAndAwait
import org.springframework.web.reactive.function.server.bodyToMono
import java.util.*

@Component
class ProjectController(
    private val projectUseCase: ProjectUseCase
) : IProjectsController, Controller {
    override suspend fun postProject(request: ServerRequest): ServerResponse =
        withAuthenticatedUser { user ->
            request.bodyToMono<ProjectRequest>().awaitSingle().let { reqBody ->
                projectUseCase.createProject(
                    name = reqBody.name,
                    description = reqBody.description
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

    override suspend fun getProjects(request: ServerRequest): ServerResponse =
        withAuthenticatedUser { user ->
            projectUseCase.getAllProjects().fold(
                {
                    ServerResponse.badRequest().bodyValueAndAwait(it.toResponse())
                },
                {
                    ServerResponse.ok().bodyValueAndAwait(
                        it.map { project -> project.toResponse() }
                    )
                }
            )
        }

    override suspend fun getTicketsByProjectId(request: ServerRequest): ServerResponse =
        withAuthenticatedUser { user ->
            projectUseCase.getTicketsByProjectId(
                projectId = UUID.fromString(request.pathVariable("projectId"))
            ).fold(
                {
                    ServerResponse.badRequest().bodyValueAndAwait(it.toResponse())
                },
                {
                    ServerResponse.ok().bodyValueAndAwait(
                        it.map { ticket -> ticket.toResponse() }
                    )
                }
            )
        }

    override suspend fun getProjectProjectId(request: ServerRequest): ServerResponse =
        withAuthenticatedUser { user ->
            projectUseCase.getProjectById(
                projectId = UUID.fromString(request.pathVariable("projectId"))
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

    override suspend fun putProjectProjectId(request: ServerRequest): ServerResponse =
        withAuthenticatedUser { user ->
            request.bodyToMono<ProjectRequest>().awaitSingle().let { reqBody ->
                projectUseCase.updateProject(
                    projectId = UUID.fromString(request.pathVariable("projectId")),
                    name = reqBody.name,
                    description = reqBody.description
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

    override suspend fun deleteProjectProjectId(request: ServerRequest): ServerResponse =
        withAuthenticatedUser { user ->
            projectUseCase.deleteProject(
                projectId = UUID.fromString(request.pathVariable("projectId"))
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

    private fun ProjectOutput.toResponse() =
        ProjectResponse(
            id = projectId,
            name = name,
            description = description
        )

    private fun TicketOutput.toResponse() =
        TicketResponse(
            ticketId = ticketId,
            projectId = projectId,
            title = title,
            content = content
        )
}
