import {useMutation, useQuery} from "@tanstack/react-query";
import {ticketRepository} from "@/repository";
import type {TicketRequest, TicketResponse} from "@/api";
import {myQueryClient} from "@/lib/tanstackQuery/QueryClient.ts";
import {useNavigate} from "@tanstack/react-router";

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
    const navigate = useNavigate()
    return useMutation({
        mutationFn: (data: TicketRequest) => ticketRepository.postTicket(data),
        onSuccess: async (data) => {
            await navigate({
                to: "/ticket/$projectId/index",
                params: {projectId: data.projectId}
            })
        }
    })
}

export const useUpdateTicket = (
    {ticketId}: {ticketId: string}
) => {
    return useMutation({
        mutationFn: (data: TicketRequest) => ticketRepository.putTicketTicketId(ticketId, data),
        onSuccess: async (data) => {
            myQueryClient.setQueryData<TicketResponse>(['ticket', data.ticketId], data)
        }
    })
}