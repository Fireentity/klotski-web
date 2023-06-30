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

/**
 * Definizione dello store Vuex
 * @type {object}
 */
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

        /**
         * Mutazione per impostare il token CSRF
         * @param {object} state - Lo stato dell'applicazione
         * @param {string} csrf - Il token CSRF
         */
        setCsrf(state, csrf: string) {
            state.csrf = csrf
        },

        /**
         * Mutazione per impostare la lista dei giochi
         * @param {object} state - Lo stato dell'applicazione
         * @param {Array<GameInfo>} games - La lista dei giochi
         */
        setGames(state, games: GameInfo[]) {
            Object.assign(state.games, games)
        },

        /**
         * Mutazione per impostare la lista delle configurazioni di partenza
         * @param {object} state - Lo stato dell'applicazione
         * @param {Array<Board>} startConfigurations - La lista delle configurazioni di partenza
         */
        setStartConfigurations(state, startConfigurations: Board[]) {
            state.startConfigurations = startConfigurations;
        },

        /**
         * Mutazione per impostare il gioco corrente
         * @param {object} state - Lo stato dell'applicazione
         * @param {Game} game - Il gioco corrente
         */
        setCurrentGame(state, game: Game) {
            Object.assign(state.currentGame, game)
        }
    },
    getters: {

        /**
         * Getter per ottenere la lista dei giochi
         * @param {object} state - Lo stato dell'applicazione
         * @returns {Array<GameInfo>} - La lista dei giochi
         */
        getGames(state): GameInfo[] {
            return state.games;
        },

        /**
         * Getter per ottenere la lista delle configurazioni di partenza
         * @param {object} state - Lo stato dell'applicazione
         * @returns {Array<Board>} - La lista delle configurazioni di partenza
         */
        getStartConfigurations(state): Board[] {
            return state.startConfigurations;
        },

        /**
         * Getter per ottenere la configurazione di partenza predefinita
         * @param {object} state - Lo stato dell'applicazione
         * @returns {Board} - La configurazione di partenza predefinita
         */
        getDefaultStartConfiguration(state): Board {
            return state.defaultStartConfiguration;
        },

        /**
         * Getter per ottenere il gioco corrente
         * @param {object} state - Lo stato dell'applicazione
         * @returns {Game} - Il gioco corrente
         */
        getCurrentGame(state): Game {
            return state.currentGame;
        },

        /**
         * Getter per ottenere le informazioni sull'utente
         * @param {object} state - Lo stato dell'applicazione
         * @returns {object} - Le informazioni sull'utente
         */
        getUser(state) {
            return state.user;
        }
    },
    actions: {

        /**
         * Azione per la registrazione di un nuovo utente
         * @param {object} context - Il contesto dell'azione Vuex
         * @param {object} payload - I dati per la registrazione
         * @param {RegisterRequest} payload.form - I dati del modulo di registrazione
         * @param {function} payload.then - La funzione da eseguire in caso di successo
         * @param {function} payload.catch - La funzione da eseguire in caso di errore
         * @returns {Promise}
         */
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

        /**
         * Azione per l'autenticazione dell'utente
         * @param {object} context - Il contesto dell'azione Vuex
         * @param {object} payload - I dati per l'autenticazione
         * @param {LoginRequest} payload.form - I dati del modulo di login
         * @param {function} payload.then - La funzione da eseguire in caso di successo
         * @param {function} payload.catch - La funzione da eseguire in caso di errore
         * @returns {Promise}
         */
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

        /**
         * Azione per il logout dell'utente
         * @param {object} context - Il contesto dell'azione Vuex
         * @returns {Promise}
         */
        async logout({getters}) {
            return axios.post('/auth/logout').then((response) => {
                //redirect logic
                if (response.status == 200) {
                    router.push('/home')
                    getters['getUser'].authenticated = false
                }
            })
        },

        /**
         * Crea un nuovo gioco.
         *
         * @async
         * @param {Object} options - Opzioni per la creazione del gioco.
         * @param {Function} options.commit - Funzione per committare le modifiche allo store.
         * @param {Function} options.getters - Funzione per ottenere lo stato dallo store.
         * @param {Function} options.dispatch - Funzione per effettuare azioni asincrone nello store.
         * @returns {Promise} - Una promise che si risolve con il risultato della richiesta HTTP.
         */
        async createGame({commit, getters, dispatch}) {
            return axios.post('/games', {
                startConfigId: getters['getDefaultStartConfiguration']
            }).then((response) => {
                commit('setCurrentGame', response.data)
                dispatch('getGameInfo', () => {
                })
            })
        },

        /**
         * Azione per ottenere le informazioni sui giochi
         * @param {object} context - Il contesto dell'azione Vuex
         * @param {function} then - La funzione da eseguire in caso di successo
         * @returns {Promise}
         */
        async getGameInfo({commit}, then: (response) => void) {
            return axios.get('/game/info').then((response) => {
                then(response)
                commit('setGames', response.data)
            })
        },

        /**
         * Azione per ottenere le informazioni di un gioco tramite ID
         * @param {object} context - Il contesto dell'azione Vuex
         * @param {number} gameId - L'ID del gioco
         * @returns {Promise}
         */
        async getGameInfoById({getters}, gameId: number) {
            return axios.get(`/game/info/${gameId}`).then((response) => {
                const games = getters['getGames'];
                const index = games.findIndex(game => game.id === gameId);
                games[index] = response.data
                getters['getCurrentGame'].moves = response.data.moves;
            })
        },

        /**
         * Azione per ottenere le configurazioni di partenza
         * @param {object} context - Il contesto dell'azione Vuex
         * @returns {Promise}
         */
        async getStartConfiguration({commit}) {
            return axios.get('/configurations').then((response) => {
                commit('setStartConfigurations', response.data)
            })
        },

        /**
         * Azione per eseguire una mossa
         * @param {object} context - Il contesto dell'azione Vuex
         * @param {MoveRequest} moveRequest - La richiesta di mossa
         * @returns {Promise}
         */
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

        /**
         * Azione per ottenere l'ultimo gioco non completato
         * @param {object} context - Il contesto dell'azione Vuex
         * @param {function} then - La funzione da eseguire in caso di successo
         * @returns {Promise}
         */
        async getLastUnfinished({commit}, then: (response) => void) {
            return axios.get('/games/unfinished').then((response) => {
                then(response);
                if (response.data.length !== 0) {
                    commit('setCurrentGame', response.data)
                }
            })
        },

        /**
         * Azione per reimpostare un gioco
         * @param {object} context - Il contesto dell'azione Vuex
         * @param {number} gameId - L'ID del gioco
         * @returns {Promise}
         */
        async reset({getters, dispatch}, gameId: number) {
            return axios.delete(`/moves?gameId=${gameId}`).then((response) => {
                const currentGame = getters['getCurrentGame'];
                currentGame.board.tiles = response.data.tiles;
                dispatch('getGameInfoById', currentGame.id);
            })
        },

        /**
         * Azione per annullare una mossa
         * @param {object} context - Il contesto dell'azione Vuex
         * @param {object} payload - I dati per l'annullamento della mossa
         * @param {UndoRequest} payload.undoRequest - La richiesta di annullamento della mossa
         * @param {function} payload.catch - La funzione da eseguire in caso di errore
         * @returns {Promise}
         */
        async undo({getters, dispatch}, payload: { undoRequest: UndoRequest, catch: (error) => void }) {
            return axios.post('/moves/undo', payload.undoRequest).then((response) => {
                const currentGame = getters['getCurrentGame'];
                currentGame.board.tiles = response.data.tiles;
                dispatch('getGameInfoById', currentGame.id);
            }).catch(error => {
                payload.catch(error);
            })
        },

        /**
         * Azione per ottenere la prossima mossa migliore
         * @param {object} context - Il contesto dell'azione Vuex
         * @param {object} payload - I dati per l'ottenimento della prossima mossa migliore
         * @param {SolveRequest} payload.solveRequest - La richiesta di ottenimento della prossima mossa migliore
         * @param {function} payload.catch - La funzione da eseguire in caso di errore
         * @returns {Promise}
         */
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

        /**
         * Azione per verificare se l'utente è autenticato
         * @param {object} context - Il contesto dell'azione Vuex
         * @param {function} then - La funzione da eseguire in caso di successo
         * @returns {Promise}
         */
        async isAuthenticated({getters}, then) {
            return axios.get('/auth/is-authenticated').then((response) => {
                getters['getUser'].authenticated = response.data
                then(response)
            })
        },

        /**
         * Azione per cambiare la configurazione di un gioco
         * @param {object} context - Il contesto dell'azione Vuex
         * @param {object} payload - I dati per il cambio della configurazione di un gioco
         * @param {ChangeGameConfigurationRequest} payload.changeRequest - La richiesta di cambio della configurazione di un gioco
         * @param {function} payload.catch - La funzione da eseguire in caso di errore
         * @returns {Promise}
         */
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

/**
 * Store Vuex principale
 * @type {object}
 */
export default createStore({
    modules: {
        main: main
    }
})