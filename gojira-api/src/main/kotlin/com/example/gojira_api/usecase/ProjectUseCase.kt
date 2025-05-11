package com.example.gojira_api.usecase

import arrow.core.Either
import arrow.core.raise.either
import com.example.gojira_api.domain.project.DeletableProjectId
import com.example.gojira_api.domain.project.Project
import com.example.gojira_api.domain.project.ProjectId
import com.example.gojira_api.domain.project.ProjectRepository
import com.example.gojira_api.domain.ticket.TicketRepository
import com.example.gojira_api.domain.user.UserId
import com.example.gojira_api.error.Error
import com.example.gojira_api.error.ErrorCode
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class ProjectUseCase(
    private val projectRepository: ProjectRepository,
    private val ticketRepository: TicketRepository
) {
    suspend fun createProject(name: String, description: String, userId: UserId): Either<UseCaseError, ProjectOutput> = either {
        val project = Project.create(name, description, userId)
            .mapLeft { UseCaseError(message = it.message, code = ErrorCode.INVALID_REQUEST) }
            .bind()
        projectRepository.createProject(project).toOutput()
    }

    suspend fun getAllProjects(userId: UserId): Either<UseCaseError, List<ProjectOutput>> = either {
        projectRepository.getAllProjectsByUserId(userId).map { it.toOutput() }
    }

    suspend fun getProjectById(projectId: UUID): Either<UseCaseError, ProjectOutput> = either {
        val project = projectRepository.getProjectById(ProjectId.reconstruct(projectId))
            ?: raise(UseCaseError(message = "Project not found", code = ErrorCode.NOT_FOUND))
        project.toOutput()
    }

    suspend fun getTicketsByProjectId(projectId: UUID): Either<UseCaseError, List<TicketOutput>> = either {
        val tickets = ticketRepository.getTicketsByProjectId(ProjectId.reconstruct(projectId))
        tickets.map { ticket ->
            TicketOutput(
                ticketId = ticket.ticketId.value,
                projectId = ticket.projectId.value,
                title = ticket.title.value,
                content = ticket.content.value
            )
        }
    }

    suspend fun updateProject(projectId: UUID, name: String, description: String): Either<UseCaseError, ProjectOutput> = either {
        val project = projectRepository.getProjectById(ProjectId.reconstruct(projectId))
            ?: raise(UseCaseError(message = "Project not found", code = ErrorCode.NOT_FOUND))

        val updatedProject = project.update(name, description)
            .mapLeft { UseCaseError(message = it.message, code = ErrorCode.INVALID_REQUEST) }
            .bind()

        projectRepository.updateProject(updatedProject).toOutput()
    }

    suspend fun deleteProject(projectId: UUID): Either<UseCaseError, Boolean> = either {
        val project = projectRepository.getProjectById(ProjectId.reconstruct(projectId))
            ?: raise(UseCaseError(message = "Project not found", code = ErrorCode.NOT_FOUND))

        val deletableProjectId = project.delete()
        projectRepository.deleteProject(deletableProjectId)
    }

    private fun Project.toOutput(): ProjectOutput {
        return ProjectOutput(
            projectId = this.projectId.value,
            name = this.name.value,
            description = this.description.value
        )
    }
}
data class ProjectOutput(
    val projectId: UUID,
    val name: String,
    val description: String
)

data class TicketOutput(
    val ticketId: UUID,
    val projectId: UUID,
    val title: String,
    val content: String
)
