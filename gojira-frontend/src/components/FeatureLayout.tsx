import * as React from "react";
import {Input} from "@/components/ui/input.tsx";

type PageLayoutProps = {
    title: string;
    createButton: React.ReactNode;
    onSearchChange: (value: string) => void;
    children: React.ReactNode;
};

export const FeatureLayout: React.FC<PageLayoutProps> = ({
                                                             title,
                                                             createButton,
                                                             onSearchChange,
                                                             children,
                                                         }) => {
    return (
        <div className="px-6 py-4">
            {/* ヘッダー */}
            <div className="flex items-center justify-between mb-6">
                <h1 className="text-2xl font-bold">{title}</h1>

                <div className="flex items-center gap-2">
                    {createButton}
                    <Input
                        type="text"
                        placeholder="検索"
                        onChange={(e) => onSearchChange(e.target.value)}
                    />
                </div>
            </div>

            {/* 本文 */}
            <div>{children}</div>
        </div>
    );
};