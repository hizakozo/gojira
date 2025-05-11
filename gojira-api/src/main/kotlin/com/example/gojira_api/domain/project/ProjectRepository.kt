package com.example.gojira_api.domain.project

import com.example.gojira_api.domain.user.UserId

interface ProjectRepository {
    suspend fun createProject(project: Project): Project
    suspend fun getProjectById(projectId: ProjectId): Project?
    suspend fun getAllProjectsByUserId(userId: UserId): List<Project>
    suspend fun updateProject(project: Project): Project
    suspend fun deleteProject(deletableProjectId: DeletableProjectId): Boolean
}