import {useMutation, useQuery} from "@tanstack/react-query";
import {projectRepository} from "@/repository";
import {QUERY_KEYS} from "@/lib/tanstackQuery/queryKeys.ts";
import type {ProjectRequest, ProjectResponse} from "src/api";
import {myQueryClient} from "@/lib/tanstackQuery/QueryClient.ts";

export const useCreateProject = (
    {
        onMutate,
    }: {
        onMutate?: (data: ProjectRequest) => void,
    } = {}
) => {
    return useMutation({
        mutationFn: (data: ProjectRequest) => projectRepository.postProject(data),
        onMutate: async (data: ProjectRequest) => {
            onMutate?.(data);
        },
        onSuccess: (data) => {
            const previousData = myQueryClient.getQueryData<ProjectResponse[]>([QUERY_KEYS.projects])
            const newData = previousData !== undefined ? [...previousData, data] : [data]
            myQueryClient.setQueryData<ProjectResponse[]>([QUERY_KEYS.projects], newData)
        }
    })
}

export const useProject = () => {
    return useQuery({
        queryFn: () => projectRepository.getProjects(),
        queryKey: [QUERY_KEYS.projects],
    })
}