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
import ChangeGameConfigurationRequest from "@/types/requests/ChangeGameConfigurationRequest.ts";

const main = {
    state: {
        csrf: '',
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
                dispatch('getGameInfo', () => {
                })
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
                if (response.data.winning) {
                    router.push('/win')
                }
            })
        },
        async getLastUnfinished({commit}, then: (response) => void) {
            return axios.get('/games/unfinished').then((response) => {
                then(response);
                if (response.data.length !== 0) {
                    commit('setCurrentGame', response.data)
                }
            })
        },
        // TODO this method is not used
        async getGame({commit}, gameId: number) {
            return axios.get(`/games?gameId=${gameId}`).then((response) => {
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
        async undo({getters, dispatch}, payload: { undoRequest: UndoRequest, catch: (error) => void }) {
            return axios.post('/moves/undo', payload.undoRequest).then((response) => {
                const currentGame = getters['getCurrentGame'];
                currentGame.board.tiles = response.data.tiles;
                dispatch('getGameInfoById', currentGame.id);
            }).catch(error => {
                payload.catch(error);
            })
        },
        async nextBestMove({getters, dispatch}, payload: { solveRequest: SolveRequest, catch: (error) => void }) {
            return axios.post('/solver', payload.solveRequest).then((response) => {
                const currentGame = getters['getCurrentGame'];
                dispatch('move', new MoveRequest(response.data.tile,
                    response.data.direction,
                    currentGame.id,
                    currentGame.board.tiles))
            }).catch(error => {
                payload.catch(error)
            })
        },
        async isAuthenticated({getters}, then) {
            return axios.get('/auth/is-authenticated').then((response) => {
                getters['getUser'].authenticated = response.data
                then(response)
            })
        },
        async changeGameConfiguration({getters}, payload: {changeRequest: ChangeGameConfigurationRequest, catch: (error) => void}) {
            return axios.patch('/games/start-configuration', payload.changeRequest).then(() => {
                Object.assign(getters['getCurrentGame'].board.tiles,
                    getters['getStartConfigurations'][payload.changeRequest.startConfigurationId].tiles);
            }).catch(error => {
                payload.catch(error);
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