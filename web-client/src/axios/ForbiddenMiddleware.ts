import {AxiosInstance} from "axios";
import router from "@/routes/Router.ts";

export default class ForbiddenMiddleware {
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