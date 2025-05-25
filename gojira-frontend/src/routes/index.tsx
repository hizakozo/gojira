import {createRootRoute, createRoute, createRouter} from "@tanstack/react-router";
import {SignUp} from "@/features/user";
import {SignIn} from "@/features/user/components/SignIn.tsx";
import {Layout} from "@/routes/Layout.tsx";
import {List} from "@/pages/project/List.tsx";

const rootRoute = createRootRoute({
    component: Layout
})
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
    component: SignUp,
})

const signInRoute = createRoute({
    getParentRoute: () => userRoute,
    path: 'sign-in',
    component: SignIn,
})

const projectRoute = createRoute({
    getParentRoute: () => rootRoute,
    path: 'project',
    component: List
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

