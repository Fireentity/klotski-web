package it.klotski.web.game.services.user.visitor;

import it.klotski.web.game.domain.tile.IRectangularTile;
import it.klotski.web.game.domain.tile.IWinningTile;

/**
 * Interfaccia che utilizza il design pattern "Visitors" per distinguere i pezzi dal loro comportamento, in modo da
 * associare a una determinata forma un determinato comportamento.
 */
public interface ITileVisitor {
    default void visitRectangularTile(IRectangularTile tileConfig) {

    }

    default void visitWinningTile(IWinningTile winningTile) {

    }
}
