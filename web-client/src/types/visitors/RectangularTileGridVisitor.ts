import RectangularTileModel from "../../types/models/RectangularTile";
import {TileVisitor} from "@/types/visitors/TileVisitor.ts";
import Result from "@/types/visitors/Result.ts";
import RectangularTile from "@/components/RectangularTile.vue";
import WinningTile from "@/types/models/WinningTile.ts";

export default class RectangularTileGridVisitor implements TileVisitor{
    result: Result

    constructor(result: Result) {
        this.result = result;
    }

    visitRectangularTile(tile: RectangularTileModel) {
        this.result.tile = tile;
        this.result.component = RectangularTile;
    }

    // @ts-ignore
    visitWinningTile(tile: WinningTile) {
    }
}