package it.klotski.web.game.services.user.visitor;

import it.klotski.web.game.services.user.strategy.IRectangularTileStrategy;
import it.klotski.web.game.services.visitor.ITileVisitor;
import it.klotski.web.game.tile.IWinningTile;
import lombok.Getter;


@Getter
public class WinningTileVisitor implements ITileVisitor {
    private final IRectangularTileStrategy insertionStrategy;

    public WinningTileVisitor(IRectangularTileStrategy insertionStrategy) {
        this.insertionStrategy = insertionStrategy;
    }

    @Override
    public void visitWinningTile(IWinningTile winningTile) {
        insertionStrategy.apply(winningTile);
    }
}
