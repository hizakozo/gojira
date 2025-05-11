package com.example.gojira_api.driver

import com.example.gojira_api.driver.gen.tables.Projects.Companion.PROJECTS
import com.example.gojira_api.driver.gen.tables.records.ProjectsRecord
import com.example.gojira_api.driver.gen.tables.records.UserProjectsRecord
import com.example.gojira_api.driver.gen.tables.references.USER_PROJECTS
import org.jooq.DSLContext
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class ProjectDriver(private val defaultDsl: DSLContext) {

    fun insert(record: ProjectsRecord, dsl: DSLContext) =
        dsl.insertInto(PROJECTS)
            .set(record)
            .execute()

    fun update(record: ProjectsRecord) =
        defaultDsl.update(PROJECTS)
            .set(record)
            .where(PROJECTS.PROJECT_ID.eq(record.projectId))
            .execute()

    fun delete(projectId: UUID) =
        defaultDsl.deleteFrom(PROJECTS)
            .where(PROJECTS.PROJECT_ID.eq(projectId))
            .execute()

    fun findAllByUserId(userId: UUID): List<ProjectsRecord> =
        defaultDsl.select(PROJECTS.PROJECT_ID, PROJECTS.NAME, PROJECTS.DESCRIPTION)
            .from(PROJECTS)
            .innerJoin(USER_PROJECTS)
            .on(PROJECTS.PROJECT_ID.eq(USER_PROJECTS.PROJECT_ID))
            .where(USER_PROJECTS.USER_ID.eq(userId))
            .fetchInto(ProjectsRecord::class.java)

    fun findById(projectId: UUID): ProjectsRecord? =
        defaultDsl.selectFrom(PROJECTS)
            .where(PROJECTS.PROJECT_ID.eq(projectId))
            .fetchOneInto(ProjectsRecord::class.java)

    fun findUserProjectsByProjectIds(projectId: List<UUID>): List<UserProjectsRecord> =
        defaultDsl.selectFrom(USER_PROJECTS)
            .where(USER_PROJECTS.PROJECT_ID.`in`(projectId))
            .fetchInto(UserProjectsRecord::class.java)

    fun insertUserProject(userProjectsRecord: UserProjectsRecord, dsl: DSLContext) =
        dsl.insertInto(USER_PROJECTS)
            .set(userProjectsRecord)
            .execute()
}