import {useProject} from "@/features/project/hooks";
import {CardDescription, CardHeader, CardTitle} from "@/components/ui/card.tsx";
import {Card} from "@/components/Card.tsx";
import {useNavigate} from "@tanstack/react-router";

export const ProjectList = () => {
    const {data: projects} = useProject()
    const navigate = useNavigate()

    if (projects === undefined) {
        return <></>
    }
    return (
        <div className="space-y-4">
            {projects.map((project) => (
                <Card
                    key={project.id}
                    onClick={async () =>{
                        await navigate({to: `/ticket/${project.id}/index`})
                    }}
                >
                    <CardHeader>
                        <CardTitle>{project.name}</CardTitle>
                        <CardDescription>{project.description}</CardDescription>
                    </CardHeader>
                </Card>
            ))}
        </div>
    );
}