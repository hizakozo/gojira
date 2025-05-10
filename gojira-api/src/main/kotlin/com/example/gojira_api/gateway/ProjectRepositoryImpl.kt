package com.example.gojira_api.gateway

import com.example.gojira_api.domain.project.DeletableProjectId
import com.example.gojira_api.domain.project.Project
import com.example.gojira_api.domain.project.ProjectId
import com.example.gojira_api.domain.project.ProjectRepository
import com.example.gojira_api.driver.ProjectDriver
import com.example.gojira_api.driver.gen.tables.records.ProjectsRecord
import org.springframework.stereotype.Component

@Component
class ProjectRepositoryImpl(
    private val projectDriver: ProjectDriver
) : ProjectRepository {
    override suspend fun createProject(project: Project): Project {
        projectDriver.insert(project.toRecord())
        return project
    }

    override suspend fun getProjectById(projectId: ProjectId): Project? =
        projectDriver.findById(projectId.value)?.toProject()


    override suspend fun getAllProjects(): List<Project> =
        projectDriver.findAll().map { it.toProject() }


    override suspend fun updateProject(project: Project): Project {
        projectDriver.update(project.toRecord())
        return project
    }


    override suspend fun deleteProject(deletableProjectId: DeletableProjectId): Boolean =
        projectDriver.delete(deletableProjectId.value) > 0


    private fun Project.toRecord() =
        ProjectsRecord(
            projectId = projectId.value,
            name = name.value,
            description = description.value
        )

    private fun ProjectsRecord.toProject() =
        Project.reconstruct(
            projectId = projectId!!,
            name = name!!,
            description = description!!
        )
}
