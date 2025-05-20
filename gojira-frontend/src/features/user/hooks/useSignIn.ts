import {AuthApi} from "../../api";
import {useAuth0} from "@auth0/auth0-react";
import {useMutation} from "@tanstack/react-query";

export const useSignIn = () => {
    const authApi = new AuthApi()
    const { getAccessTokenSilently } = useAuth0()

    const mutationFn = async () => {
        const accessToken = await getAccessTokenSilently()

        await authApi.signIn({
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