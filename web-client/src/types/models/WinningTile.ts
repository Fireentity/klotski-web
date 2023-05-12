import RectangularTile from "@/types/models/RectangularTile.ts";
import {TileVisitor} from "@/types/visitors/TileVisitor.ts";

export default class WinningTile extends RectangularTile {

    accept(visitor: TileVisitor) {
        visitor.visitWinningTile(this)
    }
}