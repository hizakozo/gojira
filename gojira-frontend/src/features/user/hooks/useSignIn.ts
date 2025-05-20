import {useMutation} from "@tanstack/react-query";
import {authRepository} from "../../repository";

export const useSignIn = (
    {getAccessToken}: {
        getAccessToken: () => Promise<string>
    }
) => {

    const mutationFn = async () => {
        const accessToken = await getAccessToken()
        const response = await authRepository.signIn({
            headers: {
                Authorization: `Bearer ${accessToken}`,
            }
        })
    }

    const { mutateAsync } = useMutation({
        mutationFn
    })
    return {
        signIn: mutateAsync
    }
}