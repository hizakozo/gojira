import {createRootRoute, createRoute, createRouter} from "@tanstack/react-router";
import {SignUp} from "@/features/user";
import {SignIn} from "@/features/user/components/SignIn.tsx";
import {Layout} from "@/routes/Layout.tsx";
import {ProjectListPage} from "@/pages/project/ProjectListPage.tsx";
import {TicketIndexPage} from "@/pages/ticket/TicketIndexPage.tsx";
import {TicketCreatePage} from "@/pages/ticket/TicketCreatePage.tsx";


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
    component: ProjectListPage
})

const ticketRoute = createRoute({
    getParentRoute: () => rootRoute,
    path: 'ticket/$projectId',
})

const ticketIndexRoute = createRoute({
    getParentRoute: () => ticketRoute,
    path: 'index',
    component: TicketIndexPage
})

const ticketCreateRoute = createRoute({
    getParentRoute: () => ticketRoute,
    path: "create",
    component: TicketCreatePage
})


const routeTree = rootRoute.addChildren([
    indexRoute,
    userRoute.addChildren([
        singUpRoute,
        signInRoute
    ]),
    projectRoute,
    ticketRoute.addChildren([
        ticketIndexRoute,
        ticketCreateRoute
    ])
])

declare module "@tanstack/react-router" {
    interface Register {
        router: typeof router;
    }
}

export const router = createRouter({ routeTree })

