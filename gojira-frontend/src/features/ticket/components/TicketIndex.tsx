import {useParams} from "@tanstack/react-router";
import {useTickets} from "@/features/ticket/hooks";
import {Card} from "@/components/Card.tsx";
import {CardContent} from "@/components/ui/card.tsx";

export const TicketIndex = () => {
    const {projectId} = useParams({from: "/ticket/$projectId/index"})
    const {data: tickets} = useTickets({projectId})
    if (tickets === undefined) {
        return <div>Loading...</div>
    }

    return (
        tickets.map((ticket, i) => (
            <Card onClick={() => {}} key={i}>
                <CardContent>
                    <p>{ticket.title}</p>
                </CardContent>
            </Card>
        ))
    )
}