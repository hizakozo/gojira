import {Auth0Provider} from "@auth0/auth0-react";

export const Auth0ProviderContainer = (
    {children}: {children: React.ReactNode}
) => {
    return <Auth0Provider
        domain=""
        clientId=""
        authorizationParams={{
            redirect_uri: "http://localhost:5173/user/sign-in"
        }}
    >
        {children}
    </Auth0Provider>
}