import {useEffect} from "react";
import {useNavigate} from "@tanstack/react-router";
import {useAuth0} from "@auth0/auth0-react";
import {setAccessToken} from "@/lib/js-cookie";
import {useSignIn} from "@/features/user/hooks/useSignIn.ts";

export const SignIn = () => {
    const {getAccessTokenSilently, isAuthenticated} = useAuth0()
    const {signIn} = useSignIn(
        {
            getAccessToken: getAccessTokenSilently,
            onSuccess: ({token}) => {
                setAccessToken(token)
            }
        }
    )
    const navigate = useNavigate()
    useEffect(() => {
        if (!isAuthenticated) {
            return
        }

        (
            async () => {
                await signIn()
                await navigate({to: '/project'})
            }
        )()
    }, [signIn, navigate, isAuthenticated])
    return <>
        <h1>Signing in...</h1>
        <p>Please wait...</p>
    </>
}