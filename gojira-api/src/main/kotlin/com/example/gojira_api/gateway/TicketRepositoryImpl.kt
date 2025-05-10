package com.example.gojira_api.gateway

import com.example.gojira_api.domain.project.ProjectId
import com.example.gojira_api.domain.ticket.DeletableTicketId
import com.example.gojira_api.domain.ticket.Ticket
import com.example.gojira_api.domain.ticket.TicketId
import com.example.gojira_api.domain.ticket.TicketRepository
import com.example.gojira_api.driver.TicketDriver
import com.example.gojira_api.driver.gen.tables.records.TicketsRecord
import com.example.gojira_api.driver.gen.tables.references.TICKETS
import org.springframework.stereotype.Component

@Component
class TicketRepositoryImpl(
    private val ticketDriver: TicketDriver
) : TicketRepository {
    override suspend fun createTicket(ticket: Ticket): Ticket {
        ticketDriver.insert(ticket.toRecord())
        return ticket
    }

    override suspend fun getTicketById(ticketId: TicketId): Ticket? =
        ticketDriver.findById(ticketId.value)?.toTicket()

    override suspend fun getTicketsByProjectId(projectId: ProjectId): List<Ticket> =
        ticketDriver.findByProjectId(projectId.value)
            .map { it.toTicket() }

    override suspend fun updateTicket(ticket: Ticket): Ticket {
        ticketDriver.update(ticket.toRecord())
        return ticket
    }

    override suspend fun deleteTicket(deletableTicketId: DeletableTicketId): Boolean =
        ticketDriver.delete(deletableTicketId.value) > 0

    private fun Ticket.toRecord() =
        TicketsRecord(
            ticketId = ticketId.value,
            projectId = projectId.value,
            title = title.value,
            content = content.value
        )

    private fun TicketsRecord.toTicket() =
        Ticket.reconstruct(
            ticketId = ticketId!!,
            projectId = projectId!!,
            title = title!!,
            content = content!!
        )
}
