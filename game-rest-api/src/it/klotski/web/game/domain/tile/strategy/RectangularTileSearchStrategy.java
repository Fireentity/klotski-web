package it.klotski.web.game.domain.tile.strategy;

import it.klotski.web.game.domain.tile.IRectangularTile;
import it.klotski.web.game.domain.tile.ITile;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class RectangularTileSearchStrategy implements IRectangularTileSearchStrategy {
    private ITile tile;
    private final int x;
    private final int y;

    @Override
    public void apply(IRectangularTile tile) {
        if(tile.getX() == x && tile.getY() == y) {
            this.tile = tile;
        }
    }

    public Optional<ITile> getTile() {
        return Optional.ofNullable(tile);
    }

}
