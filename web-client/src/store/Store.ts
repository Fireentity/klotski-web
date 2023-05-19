import axios from "@/axios/Api.ts";
import LoginRequest from "@/types/requests/LoginRequest.ts";
import RegisterRequest from "@/types/requests/RegisterRequest.ts";
import router from "@/routes/Router.ts";
import {createStore} from 'vuex'
import Config from "@/config/Config.ts";
import MoveRequest from "@/types/requests/MoveRequest.ts";
import GameInfo from "@/types/models/GameInfo.ts";
import Board from "@/types/models/Board.ts";
import Game from "@/types/models/Game.ts";

const main = {
    state() {
        return {
            csrf: '',
            board: Config.fallbackStartConfiguration,
            defaultStartConfiguration: Config.fallbackStartConfiguration.id,
            currentGame: {board: {tiles: []}},
            games: [],
            startConfigurations: [],
        }
    },
    mutations: {
        setCsrf(state, csrf: string) {
            state.csrf = csrf
        },
        setBoard(state, board: Board) {
            Object.assign(state.board.tiles, board)
        },
        setGames(state, games: GameInfo[]) {
            Object.assign(state.games, games)
        },
        setStartConfigurations(state, startConfigurations: Board[]) {
            state.startConfigurations = startConfigurations;
        },
        setCurrentGame(state, game: Game) {
            Object.assign(state.currentGame,game)
        }
    },
    getters: {
        getBoard(state): Board {
            return state.board;
        },
        getGames(state): GameInfo[] {
            return state.games;
        },
        getStartConfigurations(state): Board[] {
            return state.startConfigurations;
        },
        getDefaultStartConfiguration(state): Board {
            return state.defaultStartConfiguration;
        },
        getCurrentGame(state): Game {
            return state.currentGame;
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
                payload.then(response)
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
            return axios.post('/auth/login', payload.form,).then((response) => {
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
        async createGame({commit, getters},) {
            return axios.post('/games', {
                startConfigId: getters['getDefaultStartConfiguration']
            }).then((response) => {
                commit('setCurrentGame', response.data)
                commit('setBoard', getters['getStartConfigurations'][response.data.startConfigurationId])
            })
        },
        async getGameInfo({commit}, then: (response) => void) {
            return axios.get('/game/info').then((response) => {
                then(response)
                commit('setGames', response.data)
            })
        },
        async getStartConfiguration({commit}) {
            return axios.get('/configurations').then((response) => {
                commit('setStartConfigurations', response.data)
            })
        },
        // @ts-ignore
        async move({commit, getters}, moveRequest: MoveRequest) {
            return axios.post('/moves', moveRequest).then((response) => {
                getters['getCurrentGame'].board.tiles = response.data.tiles;
            })
        },
        // @ts-ignore
        async getLastUnfinished({commit}) {
            return axios.get('/games/unfinished').then((response) => {
                commit('setBoard', response.data.board)
                commit('setCurrentGame', response.data)
            })
        },
        async getGame({commit}, gameId: number) {
            return axios.get(`/games?gameId=${gameId}`).then((response) => {
                commit('setBoard', response.data.board)
                commit('setCurrentGame', response.data)
            })
        },
        // @ts-ignore
        async reset({commit, getters}, gameId: number) {
            return axios.delete(`/moves?gameId=${gameId}`).then((response) => {
                getters['getCurrentGame'].board.tiles = response.data.tiles;
            })
        },
        async isAuthenticated({commit}, then ){
            return axios.get('/auth/is-authenticated').then((response)=> {
                then();
            } )

        },
        // @ts-ignore
        async csrf({commit}) {
            localStorage.removeItem('X-XSRF-TOKEN')
            return axios.get('/csrf').then((response) => {
                const token = response.data.token;
                commit('setCsrf', token)
                axios.defaults.headers.common['X-XSRF-TOKEN'] = token
            })
        },
    }
}

export default createStore({
    modules: {
        main: main
    }
})