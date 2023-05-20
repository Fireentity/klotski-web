package it.klotski.web.game.tile;

import it.klotski.web.game.move.Direction;
import it.klotski.web.game.services.visitor.ITileVisitor;


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