import Tile from "@/types/models/Tile.ts";

/**
 * @class Board
 * @classdesc Rappresenta una scacchiera di gioco.
 */
export default class Board {
    /**
     * @member {number} boardWidth - Larghezza della scacchiera.
     */
    boardWidth: number;

    /**
     * @member {number} boardHeight - Altezza della scacchiera.
     */
    boardHeight: number;

    /**
     * @member {number} id - ID della scacchiera.
     */
    id: number;

    /**
     * @member {Tile[]} tiles - Array di piastrelle sulla scacchiera.
     */
    tiles: Tile[];

    /**
     * @constructor
     * @param {number} boardWidth - Larghezza della scacchiera.
     * @param {number} boardHeight - Altezza della scacchiera.
     * @param {number} id - ID della scacchiera.
     * @param {Tile[]} tiles - Array di piastrelle sulla scacchiera.
     */
    constructor(boardWidth: number, boardHeight: number, id: number, tiles: Tile[]) {
        this.boardWidth = boardWidth;
        this.boardHeight = boardHeight;
        this.id = id;
        this.tiles = tiles;
    }

}