"use client"

import {Controller, useForm} from "react-hook-form"
import { Input } from "@/components/ui/input"
import { Button } from "@/components/ui/button"
import {MarkdownShortcuts} from "@/lib/tiptap/MarkdownShortcuts.tsx";
import type {JSONContent} from "@tiptap/react";
import type {TicketRequest} from "@/api";


type Props = {
    defaultValues: TicketRequest
    onSubmit: (data: TicketRequest) => void
}
export const TicketForm = ({defaultValues, onSubmit}: Props) => {
    const { register, handleSubmit, control, watch} = useForm<TicketRequest>({
        defaultValues
    })

    const getContent = () => {
        const formContent = watch("content")
        if (formContent === undefined || formContent === "") {
            return { type: "doc", content: []}
        }
        return JSON.parse(formContent) as JSONContent
    }

    return (
        <main className="p-6 max-w-2xl mx-auto space-y-6">
            <form onSubmit={handleSubmit(onSubmit)} className="space-y-4">
                <div className={"flex justify-end items-center mb-4"}>
                    <Button type={"submit"} className={"bg-green-600 cursor-pointer hover:bg-green-700"}>作成</Button>
                </div>
                {/* タイトル */}
                <div>
                    <label className="block text-sm font-medium mb-1">タイトル</label>
                    <Input
                        {...register("title", { required: true })}
                        placeholder="チケットタイトル"
                    />
                </div>

                {/* 本文（WYSIWYGエディタ） */}
                <div>
                    <label className="block text-sm font-medium mb-1">本文</label>
                    <Controller
                        control={control}
                        name={"content"}
                        render={({field: {onChange}}) => (
                            <MarkdownShortcuts
                                content={getContent()}
                                onChange={(json) => onChange(JSON.stringify(json))}
                            />
                        )}
                    />
                </div>
            </form>
        </main>
    )
}
