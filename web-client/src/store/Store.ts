import axios from "@/store/Api.ts";
import LoginRequest from "@/types/models/LoginRequest.ts";
import RegisterRequest from "@/types/models/RegisterRequest.ts";
import Vuex from 'vuex'
import router from "@/routes/Router.ts";

const main = {
    state() {
        return {
            csrf: ''
        }
    },
    getters: {
        getCsrf(state) {
            return state.csrf
        },
        // @ts-ignore
        isAuthenticated(state) {
            return localStorage.getItem('JSESSIONID') !== undefined;
        }
    },
    mutations: {
        setCsrf(state, csrf) {
            state.csrf = csrf
        }
    },
    actions: {
        // @ts-ignore
        async register({commit}, payload: {
            form: RegisterRequest,
            then: (response) => void,
            catch: (reason) => void
        }) {
            return axios.post('/auth/register', payload.form).then((response) => {
                new Promise(resolve => {
                    resolve(response)
                }).then(() => payload.then(response))
            }).catch((reason) => {
                payload.catch(reason)
            })
        },
        async login({commit, dispatch}, payload: {
            form: LoginRequest,
            then: (response, commit) => void,
            catch: (reason) => void
        }) {
            await dispatch('csrf')
            return axios.post('/auth/login', payload.form, {withCredentials: true}).then((response) => {
                payload.then(response, commit)
            }).catch((reason) => {
                payload.catch(reason)
            })
        },
        // @ts-ignore
        async logout({commit}) {
            return axios.post('/auth/logout').then((response) => {
                //redirect logic
                if (response.status == 200) {
                    router.push({path: '/home'})
                }
            })
        },
        // @ts-ignore
        async csrf({commit}) {
            return axios.get('/csrf').then((response) => {
                const token = response.data.token;
                commit('setCsrf', token)
                axios.defaults.headers.common['X-XSRF-TOKEN'] = token
            })
        }
    }
}

export default new Vuex.Store({
    modules: {
        main: main
    }
});