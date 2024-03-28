package com.example.gojiraapi.domain.project

import arrow.core.Either
import arrow.core.NonEmptyList
import arrow.core.left
import arrow.core.right

class Project private constructor(
    val projectId: EntityId<Project>,
    val projectName: ProjectName
) {
    companion object {
        fun create(projectName: String): Either<ProjectDomainError.ProjectCreateError, Project> =
            ProjectName.create(projectName).map {
                Project(
                    projectId = EntityId.random(),
                    projectName = it
                )
            }.mapLeft {
                ProjectDomainError.ProjectCreateError(NonEmptyList(it, emptyList()))
            }
    }

    data class ProjectName(val value: String) {
        companion object {
            fun create(value: String): Either<InvalidProjectName, ProjectName> =
                if (value.isEmpty()) InvalidProjectName(
                    input = value,
                    reason = "cannot be empty"
                ).left()
                else if (value.length > 50) InvalidProjectName(
                    input = value,
                    reason = "cannot be longer than 50 characters"
                ).left()
                else ProjectName(value).right()
        }
    }
}


sealed interface ProjectDomainError : DomainError {
    data class ProjectCreateError(val causes: NonEmptyList<ValueObjectError>) : ProjectDomainError {
        override val message = ""
    }
}

data class InvalidProjectName(
    override val input: String,
    override val reason: String
) : ValueObjectError {
    override val name = "ProjectName"
}