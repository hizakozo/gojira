"use client"

import {Controller, useForm} from "react-hook-form"
import { useParams } from "@tanstack/react-router"
import { Input } from "@/components/ui/input"
import { Button } from "@/components/ui/button"
import {MarkdownShortcuts} from "@/lib/tiptap/MarkdownShortcuts.tsx";
import {type CreateTicket, useCreateTicket} from "@/features/ticket/hooks";
import type {JSONContent} from "@tiptap/react";


type Props = {
    defaultValues: CreateTicket
}
export const TicketForm = ({defaultValues}: Props) => {
    const { projectId } = useParams({ from: "/ticket/$projectId/create" })
    const { register, handleSubmit, control, watch} = useForm<CreateTicket>({
        defaultValues
    })
    const { mutateAsync } = useCreateTicket({ projectId })

    const onSubmit = async (data: CreateTicket) => {
        await mutateAsync(data)
    }

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
