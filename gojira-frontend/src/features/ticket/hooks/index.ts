import {useMutation, useQuery} from "@tanstack/react-query";
import {ticketRepository} from "@/repository";
import type {TicketRequest} from "@/api";

export const useTickets = ({projectId}: {projectId: string}) => {
    return useQuery({
        queryFn: () => ticketRepository.getTicketsByProjectId(projectId),
        queryKey: ['tickets', projectId],
    })
}

export const useTicket = ({ticketId}: {ticketId: string}) => {
    return useQuery({
        queryFn: () => ticketRepository.getTicketTicketId(ticketId),
        queryKey: ['ticket', ticketId],
    })
}

export const useCreateTicket = () => {
    return useMutation({
        mutationFn: (data: TicketRequest) => ticketRepository.postTicket(data),
    })
}

export const useUpdateTicket = (
    {ticketId}: {ticketId: string}
) => {
    return useMutation({
        mutationFn: (data: TicketRequest) => ticketRepository.putTicketTicketId(ticketId, data),
    })
}