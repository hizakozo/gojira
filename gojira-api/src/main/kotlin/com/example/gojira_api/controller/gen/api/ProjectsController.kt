package com.example.gojira_api.controller.gen.api

import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.server.coRouter

interface IProjectsController {
        suspend fun deleteProjectProjectId(request: ServerRequest): ServerResponse
        suspend fun getProjectProjectId(request: ServerRequest): ServerResponse
        suspend fun getProjects(request: ServerRequest): ServerResponse
        suspend fun getTicketsByProjectId(request: ServerRequest): ServerResponse
        suspend fun postProject(request: ServerRequest): ServerResponse
        suspend fun putProjectProjectId(request: ServerRequest): ServerResponse
}

@Configuration
class ProjectsControllerRouter {
@Bean
fun projectsControllerRoutes(controller: IProjectsController) = coRouter {
        DELETE("/projects/{projectId}", controller::deleteProjectProjectId)
        GET("/projects/{projectId}", controller::getProjectProjectId)
        GET("/projects", controller::getProjects)
        GET("/projects/{projectId}/tickets", controller::getTicketsByProjectId)
        POST("/projects", controller::postProject)
        PUT("/projects/{projectId}", controller::putProjectProjectId)
    }
}