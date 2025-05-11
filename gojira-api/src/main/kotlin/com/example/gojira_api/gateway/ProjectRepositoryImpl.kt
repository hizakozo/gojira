package com.example.gojira_api.gateway

import arrow.core.NonEmptySet
import arrow.core.Option
import arrow.core.nonEmptySetOf
import arrow.core.toNonEmptySetOrNone
import com.example.gojira_api.domain.project.DeletableProjectId
import com.example.gojira_api.domain.project.Project
import com.example.gojira_api.domain.project.ProjectId
import com.example.gojira_api.domain.project.ProjectRepository
import com.example.gojira_api.domain.user.UserId
import com.example.gojira_api.driver.ProjectDriver
import com.example.gojira_api.driver.gen.tables.records.ProjectsRecord
import com.example.gojira_api.driver.gen.tables.records.UserProjectsRecord
import org.jooq.DSLContext
import org.jooq.impl.DSL
import org.springframework.stereotype.Component

@Component
class ProjectRepositoryImpl(
    private val projectDriver: ProjectDriver,
    private val dslContext: DSLContext
) : ProjectRepository {
    override suspend fun createProject(project: Project): Project {
        dslContext.transactionResult { config ->
            val dsl = DSL.using(config)
            projectDriver.insert(project.toRecord(), dsl)
            projectDriver.insertUserProject(
                UserProjectsRecord(
                    projectId = project.projectId.value,
                    userId = project.userIds.first().value
                ),
                dsl
            )
        }
        return project
    }

    override suspend fun getProjectById(projectId: ProjectId): Project? =
        projectDriver.findById(projectId.value)?.makeProject(
            projectDriver.findUserProjectsByProjectIds(listOf(projectId.value))
        )


    override suspend fun getAllProjectsByUserId(userId: UserId): List<Project> {
        val projectRecords = projectDriver.findAllByUserId(userId.value)
        val userProjectRecords = projectDriver.findUserProjectsByProjectIds(
            projectRecords.map { it.projectId!! }
        )
        return projectRecords.map { projectRecord ->
            projectRecord.makeProject(userProjectRecords)
        }
    }


    override suspend fun updateProject(project: Project): Project {
        projectDriver.update(project.toRecord())
        return project
    }


    override suspend fun deleteProject(deletableProjectId: DeletableProjectId): Unit =
        dslContext.transactionResult { config ->
            val dsl = DSL.using(config)
            projectDriver.delete(deletableProjectId.value, dsl)
            projectDriver.deleteAllByProjectId(deletableProjectId.value, dsl)
        }


    private fun Project.toRecord() =
        ProjectsRecord(
            projectId = projectId.value,
            name = name.value,
            description = description.value
        )

    private fun ProjectsRecord.makeProject(
        userProjectRecords: List<UserProjectsRecord>,
    ): Project {
        return Project.reconstruct(
            projectId = projectId!!,
            name = name!!,
            userIds = userProjectRecords.filter { it.projectId == projectId }
                .map { UserId.reconstruct(it.userId!!) },
            description = description!!
        )
    }
}
