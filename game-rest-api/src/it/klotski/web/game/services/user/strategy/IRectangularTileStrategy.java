package it.klotski.web.game.services.user.strategy;

import it.klotski.web.game.domain.tile.IRectangularTile;

public interface IRectangularTileStrategy {
    void apply(IRectangularTile tile);
}
