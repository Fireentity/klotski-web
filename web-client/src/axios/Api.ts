import Axios from 'axios'
import StartConfigurationsMiddleware from "@/axios/StartConfigurationsMiddleware.ts";
import MovesMiddleware from "@/axios/MovesMiddleware.ts";
import GamesMiddleware from "@/axios/GamesMiddleware.ts";
import ForbiddenMiddleware from "@/axios/ForbiddenMiddleware.ts";

const axios = Axios.create({
    baseURL: `http://localhost:8000/api`,
    responseType: 'json', // default
})

axios.defaults.xsrfCookieName = undefined
axios.defaults.withCredentials = true

new StartConfigurationsMiddleware(axios)
new MovesMiddleware(axios)
new GamesMiddleware(axios)
new ForbiddenMiddleware(axios)

export default axios