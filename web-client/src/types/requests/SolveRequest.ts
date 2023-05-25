import Tile from "@/types/models/Tile.ts";

export default class SolveRequest {
    tiles: Tile[]

    constructor(tiles: Tile[]) {
        this.tiles = tiles;
    }
}