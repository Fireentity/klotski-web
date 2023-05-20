package it.klotski.web.game.services.user.visitor;

import it.klotski.web.game.services.user.strategy.IRectangularTileStrategy;
import it.klotski.web.game.services.visitor.ITileVisitor;
import it.klotski.web.game.tile.IRectangularTile;

/**
 * Visitor per la classe RectangularTile
 */
public class RectangularTileVisitor implements ITileVisitor {
    private final IRectangularTileStrategy insertionStrategy;
    /**
     * Costruttore di classe:
     * @param insertionStrategy strategia generica dei tiles di tipo IRectangularTile
     */
    public RectangularTileVisitor(IRectangularTileStrategy insertionStrategy) {
        this.insertionStrategy = insertionStrategy;
    }
    /**
     * Funzione per la visita degli oggetti di tipo IRectangularTile
     * @param tileConfig oggetto che deve essere visitato
     */
    @Override
    public void visitRectangularTile(IRectangularTile tileConfig) {
        insertionStrategy.apply(tileConfig);
    }
}
