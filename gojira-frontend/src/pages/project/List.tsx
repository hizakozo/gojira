import {FeatureLayout} from "@/components/FeatureLayout.tsx";
import {ProjectList} from "@/features/project";
import {NewProjectDialog} from "@/features/project/components/NewProjectDialog.tsx";

export const List = () => {
    return (
        <>
            <FeatureLayout
                title={"プロジェクト"}
                onSearchChange={() => {}}
                createButton={
                    <NewProjectDialog />
                }
            >
                <ProjectList />
            </FeatureLayout>
        </>
    )
}