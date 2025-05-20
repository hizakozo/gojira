import Cookies from "js-cookie";

const cookieKey = "ACCESS_TOKEN";
export const setAccessToken = (token: string) => {
    Cookies.set(cookieKey, token, {
        path: "/",
        expires: 30,
        secure: true,
        sameSite: "Lax"
    });
}

export const getAccessToken = () => {
    return Cookies.get(cookieKey);
}

export const removeAccessToken = () => {
    Cookies.remove(cookieKey, {path: "/"});
}