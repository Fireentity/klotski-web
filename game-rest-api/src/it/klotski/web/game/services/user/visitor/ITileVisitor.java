package it.klotski.web.game.services.user.visitor;

import it.klotski.web.game.domain.tile.IRectangularTile;
import it.klotski.web.game.domain.tile.IWinningTile;

public interface ITileVisitor {
    default void visitRectangularTile(IRectangularTile tileConfig) {

    }

    default void visitWinningTile(IWinningTile winningTile) {

    }
}
