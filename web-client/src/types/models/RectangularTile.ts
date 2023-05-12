import Tile from "@/types/models/Tile.ts";
import {TileVisitor} from "@/types/visitors/TileVisitor.ts";

export default class RectangularTile implements Tile {
    id: number;
    x: number;
    y: number;
    width: number;
    height: number;

    constructor(id: number, x: number, y: number, width: number, height: number) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    accept(visitor: TileVisitor) {
        visitor.visitRectangularTile(this)
    }
}