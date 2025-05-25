import {FeatureLayout} from "@/components/FeatureLayout.tsx";
import {TicketForm} from "@/features/ticket/components/TIcketForm.tsx";
import type {TicketRequest} from "@/api";
import {useParams} from "@tanstack/react-router";
import {useCreateTicket} from "@/features/ticket/hooks";


export const TicketCreatePage = () => {
    const { projectId } = useParams({ from: "/ticket/$projectId/create" })
    const { mutateAsync } = useCreateTicket()

    const defaultValues: TicketRequest= {
        projectId,
        title: "",
        content: "",
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