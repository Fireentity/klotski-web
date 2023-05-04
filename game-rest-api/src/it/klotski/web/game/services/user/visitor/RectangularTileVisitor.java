package it.klotski.web.game.services.user.visitor;

import it.klotski.web.game.domain.tile.IRectangularTile;
import it.klotski.web.game.services.user.strategy.IRectangularTileStrategy;

public class RectangularTileVisitor implements ITileVisitor {
    private final IRectangularTileStrategy insertionStrategy;

    public RectangularTileVisitor(IRectangularTileStrategy insertionStrategy) {
        this.insertionStrategy = insertionStrategy;
    }

    @Override
    public void visit(IRectangularTile tileConfig) {
        insertionStrategy.apply(tileConfig);
    }
}
