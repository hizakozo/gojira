package com.example.gojira_api.driver

import com.example.gojira_api.driver.gen.tables.records.TicketsRecord
import com.example.gojira_api.driver.gen.tables.references.TICKETS
import org.jooq.DSLContext
import org.springframework.stereotype.Component
import java.util.*

@Component
class TicketDriver(private val dsl: DSLContext) {

    fun insert(record: TicketsRecord) =
        dsl.insertInto(TICKETS)
            .set(record)
            .execute()

    fun update(record: TicketsRecord) =
        dsl.update(TICKETS)
            .set(record)
            .where(TICKETS.TICKET_ID.eq(record.ticketId))
            .execute()

    fun delete(ticketId: UUID) =
        dsl.deleteFrom(TICKETS)
            .where(TICKETS.TICKET_ID.eq(ticketId))
            .execute()

    fun findAll(): List<TicketsRecord> =
        dsl.selectFrom(TICKETS)
            .fetchInto(TicketsRecord::class.java)

    fun findById(ticketId: UUID): TicketsRecord? =
        dsl.selectFrom(TICKETS)
            .where(TICKETS.TICKET_ID.eq(ticketId))
            .fetchOneInto(TicketsRecord::class.java)

    fun findByProjectId(projectId: UUID): List<TicketsRecord> =
        dsl.selectFrom(TICKETS)
            .where(TICKETS.PROJECT_ID.eq(projectId))
            .fetchInto(TicketsRecord::class.java)
}
