import {useProject} from "@/features/project/hooks";
import {CardDescription, CardHeader, CardTitle} from "@/components/ui/card.tsx";
import {Card} from "@/components/Card.tsx";

export const ProjectList = () => {
    const {data: projects} = useProject()

    if (projects === undefined) {
        return <></>
    }
    return (
        <div className="space-y-4">
            {projects.map((project) => (
                <Card
                    key={project.id}
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