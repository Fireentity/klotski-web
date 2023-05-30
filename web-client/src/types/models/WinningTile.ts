import RectangularTile from "@/types/models/RectangularTile.ts";
import {TileVisitor} from "@/types/visitors/TileVisitor.ts";

/**
 * Rappresenta una tessera vincente, che estende la classe RectangularTile.
 */
export default class WinningTile extends RectangularTile {

    /**
     * Accetta un visitor per la tessera vincente.
     * @param {TileVisitor} visitor - Il visitor da accettare.
     */
    accept(visitor: TileVisitor) {
        visitor.visitWinningTile(this)
    }
}