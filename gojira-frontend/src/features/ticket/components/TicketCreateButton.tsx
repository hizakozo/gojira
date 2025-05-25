import {useNavigate, useParams} from "@tanstack/react-router";
import {Button} from "@/components/ui/button.tsx";

export const TicketCreateButton = () => {
    const navigate = useNavigate();
    const {projectId} = useParams({from: "/ticket/$projectId"});
    return (
        <>
            <Button
                className="bg-green-600 text-white hover:bg-green-700 cursor-pointer"
                onClick={async () => {
                    await navigate({to: "/ticket/$projectId/create", params: {projectId}})
                }}>
                チケット作成
            </Button>
        </>
    )
}
