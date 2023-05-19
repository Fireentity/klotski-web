package it.klotski.web.game.services.user.strategy;

import it.klotski.web.game.tile.IRectangularTile;
import it.klotski.web.game.tile.ITile;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RectangularTileMatrixInsertionStrategy implements IRectangularTileStrategy {
    private final ITile[][] tiles;
    private final int boardHeight;
    private final int boardWidth;

    @Override
    public void apply(IRectangularTile tile) {
        for(int i = 0; i < tile.getHeight(); i++) {
            for(int j = 0; j < tile.getWidth(); j++) {
                if(i >= boardHeight || j >= boardWidth) {
                    throw new IllegalStateException("Invalid configuration found, unable to generate response");
                }
                tiles[i+tile.getY()][j+tile.getX()] = tile;
            }
        }
    }
}
