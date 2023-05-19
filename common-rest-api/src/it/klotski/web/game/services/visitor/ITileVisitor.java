package it.klotski.web.game.services.visitor;

import it.klotski.web.game.tile.IRectangularTile;
import it.klotski.web.game.tile.IWinningTile;

public interface ITileVisitor {
    default void visitRectangularTile(IRectangularTile tileConfig) {

    }

    default void visitWinningTile(IWinningTile winningTile) {

    }
}
