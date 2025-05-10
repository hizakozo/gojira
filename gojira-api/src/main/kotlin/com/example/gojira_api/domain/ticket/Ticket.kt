package com.example.gojira_api.domain.ticket

import arrow.core.Either
import arrow.core.raise.either
import arrow.core.raise.ensure
import com.example.gojira_api.domain.DomainError
import com.example.gojira_api.domain.project.ProjectId
import java.util.UUID

class Ticket private constructor(
    val ticketId: TicketId,
    val projectId: ProjectId,
    val title: TicketTitle,
    val content: TicketContent
) {
    companion object {
        fun create(
            projectId: UUID,
            title: String,
            content: String
        ): Either<DomainError, Ticket> = either {
            val ticketTitle = TicketTitle.create(title).bind()
            val ticketContent = TicketContent.create(content).bind()
            Ticket(
                ticketId = TicketId.create(),
                projectId = ProjectId.reconstruct(projectId),
                title = ticketTitle,
                content = ticketContent
            )
        }

        fun reconstruct(
            ticketId: UUID,
            projectId: UUID,
            title: String,
            content: String
        ): Ticket {
            return Ticket(
                ticketId = TicketId.reconstruct(ticketId),
                projectId = ProjectId.reconstruct(projectId),
                title = TicketTitle.reconstruct(title),
                content = TicketContent.reconstruct(content)
            )
        }
    }

    fun update(
        title: String,
        content: String
    ): Either<DomainError, Ticket> = either {
        val ticketTitle = TicketTitle.create(title).bind()
        val ticketContent = TicketContent.create(content).bind()
        Ticket(
            ticketId = ticketId,
            projectId = projectId,
            title = ticketTitle,
            content = ticketContent
        )
    }

    fun delete(): DeletableTicketId {
        return DeletableTicketId.create(this)
    }
}

class DeletableTicketId private constructor(val value: UUID) {
    companion object {
        fun create(
            ticket: Ticket
        ) = DeletableTicketId(ticket.ticketId.value)
    }
}

class TicketId private constructor(
    val value: UUID
) {
    companion object {
        fun create(): TicketId {
            return TicketId(UUID.randomUUID())
        }

        fun reconstruct(value: UUID): TicketId {
            return TicketId(value)
        }
    }
}

class TicketTitle private constructor(
    val value: String
) {
    companion object {
        fun create(value: String): Either<DomainError, TicketTitle> = either {
            ensure(value.isNotBlank()) {
                DomainError("Ticket title cannot be blank")
            }
            ensure(value.length <= 100) {
                DomainError("Ticket title cannot be longer than 100 characters")
            }
            TicketTitle(value)
        }
        fun reconstruct(value: String): TicketTitle {
            return TicketTitle(value)
        }
    }
}

class TicketContent private constructor(
    val value: String
) {
    companion object {
        fun create(value: String): Either<DomainError, TicketContent> = either {
            ensure(value.isNotBlank()) {
                DomainError("Ticket content cannot be blank")
            }
            ensure(value.length <= 1000) {
                DomainError("Ticket content cannot be longer than 1000 characters")
            }
            TicketContent(value)
        }

        fun reconstruct(value: String): TicketContent {
            return TicketContent(value)
        }
    }
}
