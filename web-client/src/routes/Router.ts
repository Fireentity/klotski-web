import {createWebHistory, createRouter} from "vue-router";
const routes = [
    {
        path: '/:pathMatch(.*)*',
        name: 'NotFound',
        component: () => import("@/components/pages/NotFound.vue")
    },
    {
        path: "/home",
        alias: "/home",
        name: "home",
        component: () => import("@/components/pages/Home.vue")
    },
    {
        path: "/game",
        alias: "/game",
        name: "game",
        component: () => import("@/components/pages/Game.vue"),
    },
    {
        path: "/login",
        alias: "/sign-in",
        name: "login",
        component: () => import("@/components/pages/Login.vue"),
    },
    {
        path: "/register",
        alias: "/sign-up",
        name: "register",
        component: () => import("@/components/pages/Registration.vue"),
    },
];

const router = createRouter({
    history: createWebHistory(),
    routes
});

export default router