package it.klotski.web.game.services.user.strategy;

import it.klotski.web.game.domain.move.Direction;
import it.klotski.web.game.domain.tile.IRectangularTile;
import it.klotski.web.game.domain.tile.ITile;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Classe che gestisce la logica di movimento dei tiles di forma rettangolare all'interno della griglia
 */
@RequiredArgsConstructor
public class RectangularTileMoveValidationStrategy implements IRectangularTileStrategy {
    private final ITile[][] tiles;
    private final Direction direction;
    @Getter
    private boolean valid = true;

    /**
     * Funzione che verifica se il movimento di un tile è valido (non esce dalla griglia e non incrocia altri tiles)
     * @param tile il pezzo da inserire
     */

    @Override
    public void apply(IRectangularTile tile) {
        int boardHeight = tiles.length;
        int boardWidth = tiles[0].length;
        for(int i = 0; i < tile.getHeight(); i++) {
            for(int j = 0; j < tile.getWidth(); j++) {
                int calculatedX = tile.getX() + direction.getX() + j;
                int calculatedY = tile.getY() + direction.getY() + i;
                //per ogni nuova posizione del tile che si muove verifica che non esca dalla griglia
                if(calculatedY >= boardHeight || calculatedX >= boardWidth) {
                    valid = false;
                    return;
                }
                // e che non si sovrapponga ad altre tile (esclusa se stessa)
                ITile calculatedTile = tiles[calculatedY][calculatedX];
                if(calculatedTile != null && !calculatedTile.equals(tile)) {
                    valid = false;
                    return;
                }
            }
        }
    }
}
