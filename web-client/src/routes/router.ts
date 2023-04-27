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
        props: {
            user: {
                name: "lorenzo",
                games: [
                    {
                        date: 5,
                        duration: 5
                    },
                    {
                        date: 5,
                        duration: 5
                    }
                ]
            }
        }
    }
];

const router = createRouter({
    history: createWebHistory(),
    routes,
});

export default router;