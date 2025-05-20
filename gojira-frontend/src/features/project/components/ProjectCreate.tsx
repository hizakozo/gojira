import {Form} from "@/features/lib/reactHookForm/Form.tsx";
import type {ProjectRequest} from "@/features/api";
import {useCreateProject} from "@/features/project/hooks";

export const ProjectCreate = () => {
    const { mutateAsync } = useCreateProject()
    const onSubmit = async (data: ProjectRequest) => {
        await mutateAsync(data)
    }
    return (
        <Form<ProjectRequest>
            onSubmit={onSubmit}
        >
            {({register}) => (
                <>
                    <h1>Create Project</h1>
                    <div>
                        <label htmlFor="name">Name</label>
                        <input
                            type="text"
                            id="name"
                            {...register("name")}
                        />
                    </div>
                    <div>
                        <label htmlFor="description">Description</label>
                        <input
                            type="text"
                            id="description"
                            {...register("description")}
                        />
                    </div>
                    <button type="submit">Create</button>
                </>
            )}
        </Form>
    )
}