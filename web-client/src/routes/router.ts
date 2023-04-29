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
    }
];

const router = createRouter({
    history: createWebHistory(),
    routes,
});

export default router;