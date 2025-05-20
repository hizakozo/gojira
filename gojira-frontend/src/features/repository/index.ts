import {AuthApi, Configuration, ProjectApi, TicketApi} from "../api";
import {getAccessToken} from "@/features/lib/js-cookie";

const basePath = import.meta.env.VITE_API_BASE_PATH  || "http://localhost:3000";

const AuthApiConfig = new Configuration({
    basePath,
});

const ApiConfig = new Configuration({
    basePath,
    headers: {
        Authorization: `Bearer ${getAccessToken()}`
    }
})
export const authRepository = new AuthApi(AuthApiConfig)
export const projectRepository = new ProjectApi(ApiConfig)
export const ticketRepository = new TicketApi(ApiConfig)