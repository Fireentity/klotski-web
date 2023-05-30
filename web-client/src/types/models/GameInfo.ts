/**
 * @class GameInfo
 * @classdesc Rappresenta le informazioni di una partita di gioco.
 */
export default class GameInfo {
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
     * @member {number} moves - Numero di mosse effettuate durante la partita.
     */
    moves: number;

    /**
     * @member {number} duration - Durata della partita in millisecondi.
     */
    duration: number;

    /**
     * @member {boolean} finished - Flag che indica se la partita è terminata.
     */
    finished: boolean;

    /**
     * @constructor
     * @param {number} id - ID della partita.
     * @param {number} startConfigurationId - ID della configurazione di partenza.
     * @param {string} date - Data della partita.
     * @param {number} moves - Numero di mosse effettuate durante la partita.
     * @param {number} duration - Durata della partita in millisecondi.
     * @param {boolean} finished - Flag che indica se la partita è terminata.
     */
    constructor(id: number, startConfigurationId: number, date: string, moves: number, duration: number, finished: boolean) {
        this.id = id;
        this.startConfigurationId = startConfigurationId;
        this.date = date;
        this.moves = moves;
        this.duration = duration;
        this.finished = finished;
    }
}