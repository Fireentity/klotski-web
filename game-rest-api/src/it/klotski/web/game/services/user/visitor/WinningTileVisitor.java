package it.klotski.web.game.services.user.visitor;

import it.klotski.web.game.domain.tile.IWinningTile;
import it.klotski.web.game.services.user.strategy.IRectangularTileStrategy;
import lombok.Getter;

/**
 * Visitor per la classe WinningTile
 */
@Getter
public class WinningTileVisitor implements ITileVisitor {
    private final IRectangularTileStrategy insertionStrategy;

    /**
     * Costruttore di classe:
     * @param insertionStrategy strategia generica dei tiles di tipo IWinningTile
     */
    public WinningTileVisitor(IRectangularTileStrategy insertionStrategy) {
        this.insertionStrategy = insertionStrategy;
    }

    /**
     * Funzione per la visita degli oggetti di tipo IWinningTile
     * @param winningTile oggetto che deve essere visitato
     */
    @Override
    public void visitWinningTile(IWinningTile winningTile) {
        insertionStrategy.apply(winningTile);
    }
}
