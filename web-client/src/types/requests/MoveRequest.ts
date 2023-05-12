import Tile from "@/types/models/Tile.ts";
import Direction from "@/types/requests/Direction.ts";

export default class MoveRequest {
    tileToMove: Tile
    direction: Direction
    gameId: number
    tiles: Tile[]

    constructor(tileToMove: Tile, direction: Direction, gameId: number, tiles: Tile[]) {
        this.tileToMove = tileToMove;
        this.direction = direction;
        this.gameId = gameId;
        this.tiles = tiles;
    }
}