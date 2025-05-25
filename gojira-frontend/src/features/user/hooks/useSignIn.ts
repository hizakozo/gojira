import {useMutation} from "@tanstack/react-query";
import {authRepository} from "@/repository";

export const useSignIn = (
    {
        getAccessToken,
        onSuccess,
    }: {
        getAccessToken: () => Promise<string>,
        onSuccess: (arg: {token: string}) => void,
    }
) => {

    const mutationFn = async () => {
        const accessToken = await getAccessToken()
        const response = await authRepository.signIn({
            headers: {
                Authorization: `Bearer ${accessToken}`,
            }
        })
        return {token : response.token}
    }

    const { mutateAsync } = useMutation({
        mutationFn,
        onSuccess,
    })
    return {
        signIn: mutateAsync
    }
}