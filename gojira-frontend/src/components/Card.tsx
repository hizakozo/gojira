import * as React from "react";
import {Card as ShadCard} from "@/components/ui/card.tsx";
export const Card = ({children}: {
    children: React.ReactNode;
}) => {
    return <ShadCard
        className={"cursor-pointer hover:shadow-lg transition-shadow duration-200"}
    >{children}</ShadCard>;
}