import {Input} from "@/components/ui/input"
import {Textarea} from "@/components/ui/textarea"
import {BaseDialog, type BaseDialogHandle} from "@/components/BaseDialog.tsx";
import type {ProjectRequest} from "@/api";
import {useForm} from "react-hook-form";
import {useCreateProject} from "@/features/project/hooks";
import {useRef} from "react";

export const NewProjectDialog = () => {
    const baseDialogRef = useRef<BaseDialogHandle>(null)
    const {register, handleSubmit} = useForm<ProjectRequest>({
        defaultValues: {
            name: "",
            description: ""
        }
    });
    const {mutateAsync} = useCreateProject({
        onMutate: () => {
            baseDialogRef.current?.close()
        }
    })
    const onClick = async (data: ProjectRequest) => {
        await mutateAsync(data)
    }

    return (
            <BaseDialog
                ref={baseDialogRef}
                title="新しいプロジェクト"
                triggerLabel="+ 新しいプロジェクト"
                onClickRightButton={handleSubmit(onClick)}
                rightButtonLabel="作成"
            >
                    <div className="grid gap-4">
                        <div className="grid gap-2">
                            <label className="text-sm font-medium">プロジェクト名</label>
                            <Input
                                {...register("name")}
                                placeholder="プロジェクト名を入力"
                            />
                        </div>
                        <div className="grid gap-2">
                            <label className="text-sm font-medium">概要</label>
                            <Textarea
                                {...register("description")}
                                placeholder="概要を入力"
                            />
                        </div>
                    </div>
            </BaseDialog>
    )
}