package it.klotski.web.game.services.user.visitor;

import it.klotski.web.game.domain.tile.IRectangularTile;

public interface ITileVisitor {
    default void visit(IRectangularTile tileConfig) {

    }
}
