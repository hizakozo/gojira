package com.example.gojira_api.gateway

import com.example.gojira_api.domain.user.User
import com.example.gojira_api.domain.user.UserId
import com.example.gojira_api.domain.user.ExternalUserId
import com.example.gojira_api.domain.user.UserRepository
import com.example.gojira_api.driver.UserDriver
import com.example.gojira_api.driver.gen.tables.records.UsersRecord
import org.springframework.stereotype.Component

@Component
class UserRepositoryImpl(
    private val userDriver: UserDriver
) : UserRepository {
    override suspend fun create(user: User): User {
        userDriver.insert(user.toRecord())
        return user
    }

    override suspend fun findById(userId: UserId): User? =
        userDriver.findById(userId.value)?.toUser()

    override suspend fun findByExternalId(externalUserId: ExternalUserId): User? =
        userDriver.findByExternalId(externalUserId.value)?.toUser()

    override suspend fun update(user: User): User {
        userDriver.update(user.toRecord())
        return user
    }

    override suspend fun delete(userId: UserId): Boolean =
        userDriver.delete(userId.value) > 0

    private fun User.toRecord() =
        UsersRecord(
            userId = userId.value,
            externalUserId = externalUserId.value,
            email = email.value,
            name = name.value
        )

    private fun UsersRecord.toUser() =
        User.reconstruct(
            userId = userId!!,
            externalUserId = externalUserId!!,
            email = email!!,
            name = name!!
        )
}