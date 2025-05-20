import {AuthApi, Configuration} from "../api";

const basePath = import.meta.env.VITE_API_BASE_PATH  || "http://localhost:3000";

const apiConfig = new Configuration({
    basePath
});
export const authRepository = new AuthApi(apiConfig)