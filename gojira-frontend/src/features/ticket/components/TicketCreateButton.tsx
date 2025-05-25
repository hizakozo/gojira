import {useNavigate} from "@tanstack/react-router";
import {Button} from "@/components/ui/button.tsx";

export const TicketCreateButton = () => {
    const navigate = useNavigate();
    return (
        <>
            <Button
                className="bg-green-600 text-white hover:bg-green-700 cursor-pointer"
                onClick={async () => {
                    await navigate({to: "/ticket/create"})
                }}>
                チケット作成
            </Button>
        </>
    )
}
