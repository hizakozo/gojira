import {FeatureLayout} from "@/components/FeatureLayout.tsx";
import {TicketForm} from "@/features/ticket/components/TIcketForm.tsx";


export const TicketCreatePage = () => {
    const defaultValues = {
        title: "",
        content: "",
    }
    return (
        <FeatureLayout title={"チケット作成"} titleOnly={true}>
            <TicketForm defaultValues={defaultValues}/>
        </FeatureLayout>
    )
}