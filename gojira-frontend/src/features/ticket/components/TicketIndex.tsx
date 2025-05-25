import {useNavigate, useParams} from "@tanstack/react-router";
import {useTickets} from "@/features/ticket/hooks";
import {Card} from "@/components/Card.tsx";
import {CardContent} from "@/components/ui/card.tsx";

export const TicketIndex = () => {
    const {projectId} = useParams({from: "/ticket/$projectId/index"})
    const navigate = useNavigate()
    const {data: tickets} = useTickets({projectId})
    if (tickets === undefined) {
        return <div>Loading...</div>
    }

    return (
        tickets.map((ticket, i) => (
            <Card onClick={async () => {
                await navigate({
                    to: "/ticket/$projectId/edit/$ticketId",
                    params: {projectId, ticketId: ticket.ticketId}
                })
            }} key={i}>
                <CardContent>
                    <p>{ticket.title}</p>
                </CardContent>
            </Card>
        ))
    )
}