import { createWebHistory, createRouter } from "vue-router";

const routes =  [
    {
        path: "/home",
        alias: "/home",
        name: "home",
        component: () => import("@/components/pages/Home.vue")
    }
];

const router = createRouter({
    history: createWebHistory(),
    routes,
});

export default router;