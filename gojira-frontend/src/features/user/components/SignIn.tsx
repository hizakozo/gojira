import {useSignIn} from "../hooks/useSignIn.ts";
import {useEffect} from "react";
import {useNavigate} from "@tanstack/react-router";

export const SignIn = () => {
    const {signIn} = useSignIn()
    const navigate = useNavigate()

    useEffect(() => {
        signIn().then(async () => {
            await navigate({to: "/project"})
        }).catch((error) => {
            console.error("Error signing in:", error)
        })
    }, [])
    return <>
        <h1>Signing in...</h1>
        <p>Please wait...</p>
    </>
}