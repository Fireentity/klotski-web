import { createApp } from 'vue'
import App from "./App.vue";
import router from "@/routes/Router.ts";
import store from "@/store/Store.ts"
import './style.css'
createApp(App).use(router).use(store).mount('#app')