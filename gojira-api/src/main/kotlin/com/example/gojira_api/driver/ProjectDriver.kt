package com.example.gojira_api.driver

import com.example.gojira_api.driver.gen.tables.Projects.Companion.PROJECTS
import com.example.gojira_api.driver.gen.tables.records.ProjectsRecord
import org.jooq.DSLContext
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class ProjectDriver(private val dsl: DSLContext) {

    fun insert(record: ProjectsRecord) =
        dsl.insertInto(PROJECTS)
            .set(record)
            .execute()

    fun update(record: ProjectsRecord) =
        dsl.update(PROJECTS)
            .set(record)
            .where(PROJECTS.PROJECT_ID.eq(record.projectId))
            .execute()

    fun delete(projectId: UUID) =
        dsl.deleteFrom(PROJECTS)
            .where(PROJECTS.PROJECT_ID.eq(projectId))
            .execute()
    
    fun findAll(): List<ProjectsRecord> =
        dsl.selectFrom(PROJECTS)
            .fetchInto(ProjectsRecord::class.java)

    fun findById(projectId: UUID): ProjectsRecord? =
        dsl.selectFrom(PROJECTS)
            .where(PROJECTS.PROJECT_ID.eq(projectId))
            .fetchOneInto(ProjectsRecord::class.java)
}