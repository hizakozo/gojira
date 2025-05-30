import {StrictMode} from 'react'
import {createRoot} from 'react-dom/client'
import './index.css'
import {RouterProvider} from "@tanstack/react-router";
import {Auth0ProviderContainer} from "@/lib/auth0/Auth0ProviderContainer.tsx";
import {QueryClientProvider} from "@tanstack/react-query";
import {myQueryClient} from "@/lib/tanstackQuery/QueryClient.ts";
import {router} from "@/routes";


createRoot(document.getElementById('root')!).render(
    <StrictMode>
        <Auth0ProviderContainer>
            <QueryClientProvider client={myQueryClient}>
                <RouterProvider router={router}/>
            </QueryClientProvider>
        </Auth0ProviderContainer>
    </StrictMode>,
)
