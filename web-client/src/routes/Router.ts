import {createWebHistory, createRouter, RouteLocationNormalized} from "vue-router";
import Config from "@/config/Config.ts";
import store from "@/store/Store.ts"

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
        props: {
            user: {
                name: "lorenzo",
                games: [
                    {
                        date: "05/03/2023",
                        duration: 5
                    },
                    {
                        date: "07/03/2023",
                        duration: 5
                    }
                ]
            }
        }
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

router.beforeEach( (to: RouteLocationNormalized) => {
    if(!Config.publicPages.includes(to.path) && store.getters['isAuthenticated'] === false) {
        return '/login'
    }
})

export default router;