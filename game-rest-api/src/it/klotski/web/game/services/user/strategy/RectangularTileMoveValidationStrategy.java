package it.klotski.web.game.services.user.strategy;

import it.klotski.web.game.domain.move.Direction;
import it.klotski.web.game.domain.tile.IRectangularTile;
import it.klotski.web.game.domain.tile.ITile;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RectangularTileMoveValidationStrategy implements IRectangularTileStrategy {
    private final ITile[][] tiles;
    private final Direction direction;
    @Getter
    private boolean valid = true;

    @Override
    public void apply(IRectangularTile tile) {
        int boardHeight = tiles.length;
        int boardWidth = tiles[0].length;
        for(int i = 0; i < tile.getHeight(); i++) {
            for(int j = 0; j < tile.getWidth(); j++) {
                if(i >= boardHeight || j >= boardWidth) {
                    throw new IllegalStateException("Invalid configuration found, unable to generate response");
                }

                int calculatedX = tile.getX() + direction.getX() + j;
                int calculatedY = tile.getY() + direction.getY() + i;
                ITile calculatedTile = tiles[calculatedY][calculatedX];
                if(calculatedTile != null && !calculatedTile.equals(tile)) {
                    valid = false;
                }
            }
        }
    }
}
