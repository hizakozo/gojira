package com.example.gojira_api.domain.ticket

import com.example.gojira_api.domain.project.ProjectId

interface TicketRepository {
    suspend fun createTicket(ticket: Ticket): Ticket
    suspend fun getTicketById(ticketId: TicketId): Ticket?
    suspend fun getTicketsByProjectId(projectId: ProjectId): List<Ticket>
    suspend fun updateTicket(ticket: Ticket): Ticket
    suspend fun deleteTicket(deletableTicketId: DeletableTicketId): Boolean
}