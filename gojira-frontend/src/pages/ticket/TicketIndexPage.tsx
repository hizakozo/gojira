import {FeatureLayout} from "@/components/FeatureLayout.tsx";
import {TicketIndex} from "@/features/ticket/components/TicketIndex.tsx";
import {TicketCreateButton} from "@/features/ticket/components/TicketCreateButton.tsx";

export const TicketIndexPage = () => {
    return (
        <FeatureLayout
            title={"チケット"}
            onSearchChange={() => {}}
            createButton={
                <TicketCreateButton />
            }
        >
            <TicketIndex />
        </FeatureLayout>
    )
}