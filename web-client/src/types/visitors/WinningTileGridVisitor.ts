import WinningTileModel from "@/types/models/WinningTile.ts";
import {TileVisitor} from "@/types/visitors/TileVisitor.ts";
import Result from "@/types/visitors/Result.ts";
import WinningTile from "@/components/WinningTile.vue";
import RectangularTile from "@/types/models/RectangularTile.ts";

export default class WinningTileGridVisitor implements TileVisitor{
    result: Result

    constructor(result: Result) {
        this.result = result;
    }

    visitWinningTile(tile: WinningTileModel) {
        this.result.tile = tile;
        this.result.component = WinningTile;
    }

    // @ts-ignore
    visitRectangularTile(tile: RectangularTile) {
    }
}