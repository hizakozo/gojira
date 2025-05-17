import {createRootRoute, createRoute, createRouter} from "@tanstack/react-router";
import {SignUp} from "../user/signUp.tsx";

const rootRoute = createRootRoute()
const indexRoute = createRoute({
    getParentRoute: () => rootRoute,
    path: '/',
})
const userRoute = createRoute({
    getParentRoute: () => rootRoute,
    path: 'user',
})
const singUpRoute = createRoute({
    getParentRoute: () => userRoute,
    path: 'sign-up',
    component: () => <SignUp />,
})

const routeTree = rootRoute.addChildren([
    indexRoute,
    userRoute.addChildren([
        singUpRoute,
    ]),
])

declare module "@tanstack/react-router" {
    interface Register {
        router: typeof router;
    }
}

export const router = createRouter({ routeTree })

