package it.klotski.web.game.services.user.visitor;

import it.klotski.web.game.domain.tile.IWinningTile;
import it.klotski.web.game.services.user.strategy.IRectangularTileStrategy;
import lombok.Getter;

//TODO complete this class
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
