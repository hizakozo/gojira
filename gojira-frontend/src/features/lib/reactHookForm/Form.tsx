import {type DefaultValues, type FieldValues, useForm, type UseFormReturn} from "react-hook-form";
import type * as Yup from "yup";
import * as React from "react";
import {yupResolver} from "@hookform/resolvers/yup";
export type AsyncDefaultValues<TFormValues> = (payload?: unknown) => Promise<TFormValues>;
export type TYupSchema = Yup.ObjectSchema<any>
type FormProps<TFieldValues extends FieldValues, Schema> = {
    children: (methods: UseFormReturn<TFieldValues>) => React.ReactNode
    onSubmit: (data: TFieldValues, methods: UseFormReturn<TFieldValues>) => unknown | Promise<unknown>
    defaultValues?: DefaultValues<TFieldValues> | AsyncDefaultValues<TFieldValues>
    mode?: "onBlur" | "onChange" | "all",
    schema?: Schema
}

export const Form = <TFieldValues extends FieldValues = FieldValues, Schema extends TYupSchema = TYupSchema>({
    children,
    onSubmit,
    defaultValues,
    mode = "onBlur",
    schema,
}: FormProps<TFieldValues, Schema>) => {
    const methods = useForm<TFieldValues>({
        resolver: schema !== undefined ? yupResolver(schema) : undefined,
        mode,
        defaultValues
    })
    return (
        <form
            onSubmit={methods.handleSubmit((v) => {
                onSubmit(v, methods)
            })}
        >
            {children(methods)}
        </form>
    )
}