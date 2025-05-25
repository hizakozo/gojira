import {FeatureLayout} from "@/components/FeatureLayout.tsx";
import {TicketForm} from "@/features/ticket/components/TIcketForm.tsx";
import {useParams} from "@tanstack/react-router";
import {useTicket, useUpdateTicket} from "@/features/ticket/hooks";
import type {TicketRequest} from "@/api";


export const TicketEditPage = () => {
    const {ticketId} = useParams({from: "/ticket/$projectId/edit/$ticketId"})
    const {data: ticket} = useTicket({ticketId})
    const {mutateAsync} = useUpdateTicket({ticketId})
    if (ticket === undefined) {
        return <></>
    }
    const defaultValues: TicketRequest = {
        projectId: ticket.projectId,
        title: ticket.title,
        content: ticket.content,
    }
    const onSubmit = async (data: TicketRequest) => {
        await mutateAsync(data)
    }
    return (
        <FeatureLayout title={"チケット作成"} titleOnly={true}>
            <TicketForm defaultValues={defaultValues} onSubmit={onSubmit}/>
        </FeatureLayout>
    )
}