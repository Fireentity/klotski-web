package it.klotski.web.game.domain.tile.strategy;

import it.klotski.web.game.domain.tile.IWinningTile;

public interface IWinningTileStrategy {
    /**
     * Applica la strategia al tile rettangolare specificato.
     *
     * @param tile il tile rettangolare a cui applicare la strategia
     */
    void apply(IWinningTile tile);
}
