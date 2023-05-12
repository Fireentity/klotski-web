import RectangularTile from "@/types/models/RectangularTile.ts";
import WinningTile from "@/types/models/WinningTile.ts";

export interface TileVisitor {
    visitRectangularTile(tile: RectangularTile);
    visitWinningTile(tile: WinningTile)
}