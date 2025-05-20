import {useSignIn} from "../hooks/useSignIn.ts";
import {useEffect} from "react";
import {useNavigate} from "@tanstack/react-router";
import {useAuth0} from "@auth0/auth0-react";

export const SignIn = () => {
    const {getAccessTokenSilently, isAuthenticated} = useAuth0()
    const {signIn} = useSignIn({getAccessToken: getAccessTokenSilently})
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