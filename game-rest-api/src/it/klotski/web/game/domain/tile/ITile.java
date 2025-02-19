package it.klotski.web.game.domain.tile;

import it.klotski.web.game.domain.move.Direction;
import it.klotski.web.game.domain.tile.visitor.ITileVisitor;


/**
 * Questa interfaccia rappresenta una tessera del gioco.
 */
public interface ITile extends Comparable<ITile> {
    /**
     * Restituisce l'ID della tessera.
     *
     * @return l'ID della tessera
     */
    int getId();

    /**
     * Accetta un visitor per la tessera.
     *
     * @param visitor il visitor
     */
    void accept(ITileVisitor visitor);

    /**
     * Sposta la tessera in una direzione specificata.
     *
     * @param direction la direzione in cui spostare la tessera
     * @return la tessera spostata
     */
    RectangularTile move(Direction direction);

}