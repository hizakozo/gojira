import {forwardRef, type ReactNode, useImperativeHandle, useState} from "react";
import {Dialog, DialogTrigger} from "@radix-ui/react-dialog";
import {Button} from "@/components/ui/button.tsx";
import {DialogContent, DialogFooter, DialogHeader, DialogTitle} from "@/components/ui/dialog.tsx";

export type OneClickRightButtonResult = "close" | "continue"
type Props = {
    title: string
    triggerLabel: string
    rightButtonLabel: string
    onClickRightButton: () => void
    children: ReactNode
    onOpenChange?: (open: boolean) => void
}
export type BaseDialogHandle = {
    close: () => void
}

export const BaseDialog = forwardRef<BaseDialogHandle, Props>(({
                               title,
                               triggerLabel,
                               children,
                               onOpenChange,
    rightButtonLabel,
    onClickRightButton
                           }, ref) => {
    const [open, setOpen] = useState(false)
    useImperativeHandle(ref, () => ({
        close() {
            handleOpenChange(false)
        },
    }))

    const handleOpenChange = (value: boolean) => {
        setOpen(value)
        onOpenChange?.(value)
    }

    return (
        <Dialog open={open} onOpenChange={handleOpenChange}>
            <DialogTrigger asChild>
                <Button
                    className="bg-green-600 text-white hover:bg-green-700 cursor-pointer"
                >
                    {triggerLabel}
                </Button>
            </DialogTrigger>
            <DialogContent className="sm:max-w-md">
                <DialogHeader>
                    <DialogTitle>{title}</DialogTitle>
                </DialogHeader>
                <div className="py-4">{children}</div>
            <DialogFooter>
                <Button variant="outline" onClick={
() => handleOpenChange(false)
                }>キャンセル</Button>
                <Button
                    className="bg-green-600 text-white hover:bg-green-700"
                    onClick={onClickRightButton}
                >
                    {rightButtonLabel}
                </Button>
            </DialogFooter>
            </DialogContent>
        </Dialog>
    )
})