import {useQuery} from "@tanstack/react-query";
import {ticketRepository} from "@/repository";

export const useTickets = ({projectId}: {projectId: string}) => {
    return useQuery({
        queryFn: () => ticketRepository.getTicketsByProjectId(projectId),
        queryKey: ['tickets', projectId],
    })
}