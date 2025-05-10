package com.example.gojira_api.domain.user

interface UserRepository {
    suspend fun create(user: User): User
    suspend fun findById(userId: UserId): User?
    suspend fun findByExternalId(externalUserId: ExternalUserId): User?
    suspend fun update(user: User): User
    suspend fun delete(userId: UserId): Boolean
}