import Tile from "@/types/models/Tile.ts";

export default class UndoRequest {
    gameId: number
    tiles: Tile

    constructor(gameId: number, tiles: Tile) {
        this.gameId = gameId;
        this.tiles = tiles;
    }
}