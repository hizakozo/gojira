import {createRootRoute, createRoute, createRouter} from "@tanstack/react-router";
import {SignUp} from "../user";
import {ProjectList} from "../project";
import {SignIn} from "../user/components/SignIn.tsx";

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

const signInRoute = createRoute({
    getParentRoute: () => userRoute,
    path: 'sign-in',
    component: () => <SignIn />,
})

const projectRoute = createRoute({
    getParentRoute: () => rootRoute,
    path: 'project',
    component: () => <ProjectList />
})

const routeTree = rootRoute.addChildren([
    indexRoute,
    userRoute.addChildren([
        singUpRoute,
        signInRoute
    ]),
    projectRoute
])

declare module "@tanstack/react-router" {
    interface Register {
        router: typeof router;
    }
}

export const router = createRouter({ routeTree })

