import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import './index.css'
import {RouterProvider} from "@tanstack/react-router";
import {router} from "./features/router";
import {Auth0ProviderContainer} from "./features/auth0/Auth0ProviderContainer.tsx";

createRoot(document.getElementById('root')!).render(
  <StrictMode>
      <Auth0ProviderContainer>
    <RouterProvider router={router} />
        </Auth0ProviderContainer>
  </StrictMode>,
)
