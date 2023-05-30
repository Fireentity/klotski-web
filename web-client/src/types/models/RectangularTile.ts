import Tile from "@/types/models/Tile.ts";
import {TileVisitor} from "@/types/visitors/TileVisitor.ts";

/**
 * Rappresenta una tessera rettangolare.
 */
export default class RectangularTile implements Tile {
    /**
     * Identificatore della tessera.
     */
    id: number;

    /**
     * Posizione x della tessera.
     */
    x: number;

    /**
     * Posizione y della tessera.
     */
    y: number;

    /**
     * Larghezza della tessera.
     */
    width: number;

    /**
     * Altezza della tessera.
     */
    height: number;

    /**
     * Crea un'istanza di RectangularTile.
     * @param {number} id - Identificatore della tessera.
     * @param {number} x - Posizione x della tessera.
     * @param {number} y - Posizione y della tessera.
     * @param {number} width - Larghezza della tessera.
     * @param {number} height - Altezza della tessera.
     */
    constructor(id: number, x: number, y: number, width: number, height: number) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    /**
     * Accetta un visitor per la tessera rettangolare.
     * @param {TileVisitor} visitor - Il visitor da accettare.
     */
    accept(visitor: TileVisitor) {
        visitor.visitRectangularTile(this)
    }
}