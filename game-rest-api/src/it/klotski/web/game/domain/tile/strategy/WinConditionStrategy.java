package it.klotski.web.game.domain.tile.strategy;

import it.klotski.web.game.domain.tile.IRectangularTile;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class WinConditionStrategy implements IRectangularTileStrategy {
    private final int winningX;
    private final int winningY;
    private boolean winning = false;

    @Override
    public void apply(IRectangularTile tile) {
        if(tile.getX() == winningX && tile.getY() == winningY) {
            winning = true;
        }
    }
}
