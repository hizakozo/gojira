import {Auth0Provider} from "@auth0/auth0-react";

const domain = import.meta.env.VITE_AUTH0_DOMAIN
const clientId = import.meta.env.VITE_AUTH0_CLIENT_ID

export const Auth0ProviderContainer = (
    {children}: {children: React.ReactNode}
) => {
    return <Auth0Provider
        domain={domain}
        clientId={clientId}
        authorizationParams={{
            redirect_uri: "http://localhost:5173/user/sign-in"
        }}
    >
        {children}
    </Auth0Provider>
}