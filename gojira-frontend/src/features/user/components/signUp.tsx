import {useAuth0} from "@auth0/auth0-react";

export const SignUp = () => {
    const { loginWithRedirect } = useAuth0();

    return (
        <div>
        <h1>Sign Up</h1>
        <p>Sign up page content goes here.</p>
            <button onClick={() => loginWithRedirect()}>Log In</button>
        </div>
    );
}