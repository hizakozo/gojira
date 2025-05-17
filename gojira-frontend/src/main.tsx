import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import './index.css'
import {RouterProvider} from "@tanstack/react-router";
import {router} from "./features/router";
import {Auth0Provider} from "@auth0/auth0-react";

createRoot(document.getElementById('root')!).render(
  <StrictMode>
      <Auth0Provider
          domain=""
          clientId=""
          authorizationParams={{
              redirect_uri: "http://localhost:5173/project"
          }}
      >
    <RouterProvider router={router} />
        </Auth0Provider>
  </StrictMode>,
)
