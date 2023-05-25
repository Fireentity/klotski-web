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
import UndoRequest from "@/types/requests/UndoRequest.ts";
import SolveRequest from "@/types/requests/SolveRequest.ts";

const main = {
    state: {
        csrf: '',
        board: Config.fallbackStartConfiguration,
        defaultStartConfiguration: Config.fallbackStartConfiguration.id,
        currentGame: {board: {tiles: []}},
        games: [],
        startConfigurations: [],
        user: {authenticated: false}
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
            Object.assign(state.currentGame, game)
        }
    },
    getters: {
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
        },
        getUser(state) {
            return state.user;
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
        async login({commit, dispatch, getters}, payload: {
            form: LoginRequest,
            then: (response, commit) => void,
            catch: (reason) => void
        }) {
            await dispatch('csrf')
            return axios.post('/auth/login', payload.form,).then((response) => {
                payload.then(response, commit)
                getters['getUser'].authenticated = true
            }).catch((reason) => {
                payload.catch(reason)
            })
        },
        async logout({getters}) {
            return axios.post('/auth/logout').then((response) => {
                //redirect logic
                if (response.status == 200) {
                    router.push('/home')
                    getters['getUser'].authenticated = false
                }
            })
        },
        async createGame({commit, getters, dispatch}) {
            return axios.post('/games', {
                startConfigId: getters['getDefaultStartConfiguration']
            }).then((response) => {
                commit('setCurrentGame', response.data)
                commit('setBoard', getters['getStartConfigurations'][response.data.startConfigurationId])
                dispatch('getGameInfo', () => {})
            })
        },
        async getGameInfo({commit}, then: (response) => void) {
            return axios.get('/game/info').then((response) => {
                then(response)
                commit('setGames', response.data)
            })
        },
        async getGameInfoById({getters}, gameId: number) {
            return axios.get(`/game/info/${gameId}`).then((response) => {
                const games = getters['getGames'];
                const index = games.findIndex(game => game.id === gameId);
                games[index] = response.data
                getters['getCurrentGame'].moves = response.data.moves;
            })
        },
        async getStartConfiguration({commit}) {
            return axios.get('/configurations').then((response) => {
                commit('setStartConfigurations', response.data)
            })
        },
        async move({getters, dispatch}, moveRequest: MoveRequest) {
            return axios.post('/moves', moveRequest).then((response) => {
                const currentGame = getters['getCurrentGame'];
                currentGame.board.tiles = response.data.tiles;
                dispatch('getGameInfoById', currentGame.id);
            })
        },
        async getLastUnfinished({commit}) {
            return axios.get('/games/unfinished').then((response) => {
                commit('setBoard', response.data.board)
                commit('setCurrentGame', response.data)
            })
        },
        // TODO this method is not used
        async getGame({commit}, gameId: number) {
            return axios.get(`/games?gameId=${gameId}`).then((response) => {
                commit('setBoard', response.data.board)
                commit('setCurrentGame', response.data)
            })
        },
        async reset({getters, dispatch}, gameId: number) {
            return axios.delete(`/moves?gameId=${gameId}`).then((response) => {
                const currentGame = getters['getCurrentGame'];
                currentGame.board.tiles = response.data.tiles;
                dispatch('getGameInfoById', currentGame.id);
            })
        },
        async undo({getters, dispatch}, undoRequest: UndoRequest) {
            return axios.post('/moves/undo', undoRequest).then((response) => {
                const currentGame = getters['getCurrentGame'];
                currentGame.board.tiles = response.data.tiles;
                dispatch('getGameInfoById', currentGame.id);
            })
        },
        async nextBestMove({getters, dispatch}, solveRequest: SolveRequest) {
            return axios.post('/solver', solveRequest).then((response) => {
                const currentGame = getters['getCurrentGame'];
                dispatch('move', new MoveRequest(response.data.tile,
                    response.data.direction,
                    currentGame.id,
                    currentGame.board.tiles))
            })
        },
        async isAuthenticated({getters}, then) {
            return axios.get('/auth/is-authenticated').then((response) => {
                getters['getUser'].authenticated = response.data
                then(response)
            })
        },
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