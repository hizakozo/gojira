package com.example.gojira_api.driver

import com.example.gojira_api.driver.gen.tables.Users.Companion.USERS
import com.example.gojira_api.driver.gen.tables.records.UsersRecord
import org.jooq.DSLContext
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class UserDriver(private val dsl: DSLContext) {

    fun insert(record: UsersRecord) =
        dsl.insertInto(USERS)
            .set(record)
            .execute()

    fun update(record: UsersRecord) =
        dsl.update(USERS)
            .set(record)
            .where(USERS.USER_ID.eq(record.userId))
            .execute()

    fun delete(userId: UUID) =
        dsl.deleteFrom(USERS)
            .where(USERS.USER_ID.eq(userId))
            .execute()
    
    fun findAll(): List<UsersRecord> =
        dsl.selectFrom(USERS)
            .fetchInto(UsersRecord::class.java)

    fun findById(userId: UUID): UsersRecord? =
        dsl.selectFrom(USERS)
            .where(USERS.USER_ID.eq(userId))
            .fetchOneInto(UsersRecord::class.java)
            
    fun findByExternalId(externalUserId: String): UsersRecord? =
        dsl.selectFrom(USERS)
            .where(USERS.EXTERNAL_USER_ID.eq(externalUserId))
            .fetchOneInto(UsersRecord::class.java)
}