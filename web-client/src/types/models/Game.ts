import Board from "@/types/models/Board.ts";

/**
 * @class Game
 * @classdesc Rappresenta una partita di gioco.
 */
export default class Game {
    /**
     * @member {number} id - ID della partita.
     */
    id: number;

    /**
     * @member {number} startConfigurationId - ID della configurazione di partenza.
     */
    startConfigurationId: number;

    /**
     * @member {string} date - Data della partita.
     */
    date: string;

    /**
     * @member {boolean} finished - Flag che indica se la partita è terminata.
     */
    finished: boolean;

    /**
     * @member {number} moves - Numero di mosse effettuate durante la partita.
     */
    moves: number;

    /**
     * @member {Board} board - La scacchiera di gioco.
     */
    board: Board;

    /**
     * @constructor
     * @param {number} id - ID della partita.
     * @param {number} startConfigurationId - ID della configurazione di partenza.
     * @param {string} date - Data della partita.
     * @param {boolean} finished - Flag che indica se la partita è terminata.
     * @param {number} moves - Numero di mosse effettuate durante la partita.
     * @param {Board} board - La scacchiera di gioco.
     */
    constructor(id: number, startConfigurationId: number, date: string, finished: boolean, moves: number, board: Board) {
        this.id = id;
        this.startConfigurationId = startConfigurationId;
        this.date = date;
        this.finished = finished;
        this.moves = moves;
        this.board = board;
    }
}