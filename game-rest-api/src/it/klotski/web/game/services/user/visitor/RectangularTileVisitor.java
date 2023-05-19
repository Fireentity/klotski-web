package it.klotski.web.game.services.user.visitor;

import it.klotski.web.game.services.user.strategy.IRectangularTileStrategy;
import it.klotski.web.game.services.visitor.ITileVisitor;
import it.klotski.web.game.tile.IRectangularTile;


public class RectangularTileVisitor implements ITileVisitor {
    private final IRectangularTileStrategy insertionStrategy;

    public RectangularTileVisitor(IRectangularTileStrategy insertionStrategy) {
        this.insertionStrategy = insertionStrategy;
    }

    @Override
    public void visitRectangularTile(IRectangularTile tileConfig) {
        insertionStrategy.apply(tileConfig);
    }
}
