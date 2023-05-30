import {AxiosInstance} from "axios";
import router from "@/routes/Router.ts";

export default class ForbiddenMiddleware {
    /**
     * Crea un'istanza del middleware Forbidden.
     * @param axios - L'istanza di Axios a cui il middleware deve essere applicato.
     */
    constructor(axios: AxiosInstance) {
        axios.interceptors.response.use(response => {
            console.log('ciao')
                if(response.status === 403) {
                    router.push('/login')
                    console.log('ciao')
                }
                return response;
            }
        );
    }
}