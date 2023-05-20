import router from "@/routes/Router.ts";
import {AxiosInstance} from "axios";


export default class ForbiddenMiddleware {
    constructor(axios: AxiosInstance) {
        axios.interceptors.response.use(
            response => {
                return response;
            },
            error => {
                if (error.response.status === 403) {
                    //router.push({path: "/login"})
                }
                return Promise.reject(error);
            }
        );
    }
}