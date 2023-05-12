import Tile from "@/types/models/Tile.ts";

export default class Board {
    boardWidth: number;
    boardHeight: number;
    id: number;
    tiles: Tile[]

    constructor(boardWidth: number, boardHeight: number, id: number, tiles: Tile[]) {
        this.boardWidth = boardWidth;
        this.boardHeight = boardHeight;
        this.id = id;
        this.tiles = tiles;
    }

}