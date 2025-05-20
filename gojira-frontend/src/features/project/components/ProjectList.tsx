import {useProject} from "@/features/project/hooks";
import {ProjectCreate} from "@/features/project/components/ProjectCreate.tsx";

export const ProjectList = () => {
    const { data } = useProject()

    if (data === undefined) {
        return <></>
    }
    return (
        <div>

            <ProjectCreate />

            <h1>Project List</h1>
            {
                data.map((project) => (
                    <div key={project.id}>
                        <h2>{project.name}</h2>
                        <p>{project.description}</p>
                    </div>
                ))
            }
        </div>
    );
}