import {Outlet} from "@tanstack/react-router";

export const Layout = () => {
    return (
        <div className={"bg-white pl-50 pr-50 h-screen"}>
            <div className={"bg-gray-50 h-screen"}>
            <Outlet />
            </div>
        </div>
    )
}