package it.klotski.web.game.domain.tile.visitor;

import it.klotski.web.game.domain.tile.IRectangularTile;
import it.klotski.web.game.domain.tile.strategy.IRectangularTileStrategy;

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
