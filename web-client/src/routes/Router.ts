import {createWebHistory, createRouter} from "vue-router";
import Config from "@/config/Config.ts";
import store from "@/store/Store.ts";

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
    {
        path: "/rules",
        name: "rules",
        component: () => import("@/components/pages/Rules.vue"),
    },
];

const router = createRouter({
    history: createWebHistory(),
    routes
});

router.beforeEach((to) => {
    store.dispatch('isAuthenticated', (response) => {
        if(response.data === false && !Config.publicPages.includes(to.path)) {
            router.push('/login')
        }
    })
})
export default router