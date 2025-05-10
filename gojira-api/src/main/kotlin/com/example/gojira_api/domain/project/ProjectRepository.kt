package com.example.gojira_api.domain.project

interface ProjectRepository {
    suspend fun createProject(project: Project): Project
    suspend fun getProjectById(projectId: ProjectId): Project?
    suspend fun getAllProjects(): List<Project>
    suspend fun updateProject(project: Project): Project
    suspend fun deleteProject(deletableProjectId: DeletableProjectId): Boolean
}