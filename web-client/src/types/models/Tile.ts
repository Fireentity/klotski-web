import {TileVisitor} from "@/types/visitors/TileVisitor.ts";
/**
 * Rappresenta una tessera.
 */
export default interface Tile {
    /**
     * Accetta un visitor per la tessera.
     * @param {TileVisitor} visitor - Il visitor da accettare.
     */
    accept(visitor: TileVisitor)
}