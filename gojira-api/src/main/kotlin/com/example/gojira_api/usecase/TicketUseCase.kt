package com.example.gojira_api.usecase

import arrow.core.Either
import arrow.core.raise.either
import com.example.gojira_api.domain.project.ProjectId
import com.example.gojira_api.domain.ticket.DeletableTicketId
import com.example.gojira_api.domain.ticket.Ticket
import com.example.gojira_api.domain.ticket.TicketId
import com.example.gojira_api.domain.ticket.TicketRepository
import com.example.gojira_api.error.ErrorCode
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class TicketUseCase(
    private val ticketRepository: TicketRepository
) {
    suspend fun createTicket(projectId: UUID, title: String, content: String): Either<UseCaseError, TicketOutput> = either {
        val ticket = Ticket.create(projectId, title, content)
            .mapLeft { UseCaseError(message = it.message, code = ErrorCode.INVALID_REQUEST) }
            .bind()
        ticketRepository.createTicket(ticket).toOutput()
    }

    suspend fun getTicketById(ticketId: UUID): Either<UseCaseError, TicketOutput> = either {
        val ticket = ticketRepository.getTicketById(TicketId.reconstruct(ticketId))
            ?: raise(UseCaseError(message = "Ticket not found", code = ErrorCode.NOT_FOUND))
        ticket.toOutput()
    }

    suspend fun updateTicket(ticketId: UUID, title: String, content: String): Either<UseCaseError, TicketOutput> = either {
        val ticket = ticketRepository.getTicketById(TicketId.reconstruct(ticketId))
            ?: raise(UseCaseError(message = "Ticket not found", code = ErrorCode.NOT_FOUND))

        val updatedTicket = ticket.update(title, content)
            .mapLeft { UseCaseError(message = it.message, code = ErrorCode.INVALID_REQUEST) }
            .bind()

        ticketRepository.updateTicket(updatedTicket).toOutput()
    }

    suspend fun deleteTicket(ticketId: UUID): Either<UseCaseError, Boolean> = either {
        val ticket = ticketRepository.getTicketById(TicketId.reconstruct(ticketId))
            ?: raise(UseCaseError(message = "Ticket not found", code = ErrorCode.NOT_FOUND))

        val deletableTicketId = ticket.delete()
        ticketRepository.deleteTicket(deletableTicketId)
    }

    private fun Ticket.toOutput(): TicketOutput {
        return TicketOutput(
            ticketId = this.ticketId.value,
            projectId = this.projectId.value,
            title = this.title.value,
            content = this.content.value
        )
    }
}