import Axios from 'axios'

const axios = Axios.create({
    baseURL: 'http://localhost:8000/api',
    responseType: 'json', // default
})
axios.defaults.xsrfCookieName = undefined
axios.defaults.withCredentials = true
export default axios