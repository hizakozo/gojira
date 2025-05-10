package com.example.gojira_api.domain.project

import arrow.core.Either
import arrow.core.raise.either
import arrow.core.raise.ensure
import com.example.gojira_api.domain.DomainError
import java.util.UUID

class Project private constructor(
    val projectId: ProjectId,
    val name: ProjectName,
    val description: ProjectDescription
) {
    companion object {
        fun create(
            name: String,
            description: String
        ): Either<DomainError, Project> = either {
            val projectId = ProjectId.create()
            val projectName = ProjectName.create(name).bind()
            val projectDescription = ProjectDescription.create(description).bind()
            Project(
                projectId = projectId,
                name = projectName,
                description = projectDescription
            )
        }

        fun reconstruct(
            projectId: UUID,
            name: String,
            description: String
        ): Project {
            return Project(
                projectId = ProjectId.reconstruct(projectId),
                name = ProjectName.reconstruct(name),
                description = ProjectDescription.reconstruct(description)
            )
        }
    }

    fun update(
        name: String,
        description: String
    ): Either<DomainError, Project> = either {
        val projectName = ProjectName.create(name).bind()
        val projectDescription = ProjectDescription.create(description).bind()
        Project(
            projectId = projectId,
            name = projectName,
            description = projectDescription
        )
    }
    fun delete(): DeletableProjectId {
        return DeletableProjectId.create(this)
    }
}

class DeletableProjectId private constructor(val value: UUID) {
    companion object {
        fun create(
            project: Project
        ) = DeletableProjectId(project.projectId.value)
    }
}

class ProjectId private constructor(
    val value: UUID
) {
    companion object {
        fun create(): ProjectId {
            return ProjectId(UUID.randomUUID())
        }

        fun reconstruct(value: UUID): ProjectId {
            return ProjectId(value)
        }
    }
}

class ProjectName private constructor(
    val value: String
) {
    companion object {
        fun create(value: String): Either<DomainError, ProjectName> = either {
            ensure(value.isNotBlank()) {
                DomainError("Project name cannot be blank")
            }
            ensure(value.length <= 100) {
                DomainError("Project name cannot be longer than 100 characters")
            }
            ProjectName(value)
        }
        fun reconstruct(value: String): ProjectName {
            return ProjectName(value)
        }
    }
}

class ProjectDescription private constructor(
    val value: String
) {
    companion object {
        fun create(value: String): Either<DomainError, ProjectDescription> = either {
            ensure(value.isNotBlank()) {
                DomainError("Project description cannot be blank")
            }
            ensure(value.length <= 500) {
                DomainError("Project description cannot be longer than 500 characters")
            }
            ProjectDescription(value)
        }

        fun reconstruct(value: String): ProjectDescription {
            return ProjectDescription(value)
        }
    }
}
