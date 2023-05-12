import Axios from 'axios'
import ForbiddenMiddleware from "@/axios/ForbiddenMiddleware.ts";
import StartConfigurationsMiddleware from "@/axios/StartConfigurationsMiddleware.ts";
import MovesMiddleware from "@/axios/MovesMiddleware.ts";
import GamesMiddleware from "@/axios/GamesMiddleware.ts";

const axios = Axios.create({
    baseURL: 'http://localhost:8000/api',
    responseType: 'json', // default
})
//TODO ma ti pare che devo settare le cose undefined altrimenti sto coso fa le robe per conto suo
// Controllare se esiste un modo migliore di fare sta porcata
axios.defaults.xsrfCookieName = undefined
axios.defaults.withCredentials = true

new ForbiddenMiddleware(axios)
new StartConfigurationsMiddleware(axios)
new MovesMiddleware(axios)
new GamesMiddleware(axios)

export default axios