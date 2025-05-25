import {useMutation, useQuery} from "@tanstack/react-query";
import {ticketRepository} from "@/repository";
import type {TicketRequest} from "@/api";

export const useTickets = ({projectId}: {projectId: string}) => {
    return useQuery({
        queryFn: () => ticketRepository.getTicketsByProjectId(projectId),
        queryKey: ['tickets', projectId],
    })
}

export type CreateTicket = Omit<TicketRequest, "projectId">
export const useCreateTicket = ({projectId}: {projectId: string}) => {
    return useMutation({
        mutationFn: (data: CreateTicket) => ticketRepository.postTicket({
            ...data,
            projectId, // Ensure projectId is included
        }),
    })
}