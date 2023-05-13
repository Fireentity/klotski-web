package it.klotski.web.game.services.user.strategy;

import it.klotski.web.game.domain.tile.IRectangularTile;
import it.klotski.web.game.domain.tile.ITile;
import lombok.RequiredArgsConstructor;
/**
 * Classe che gestisce la logica d'inserimento dei tiles di forma rettangolare all'interno della griglia
 */
@RequiredArgsConstructor
public class RectangularTileMatrixInsertionStrategy implements IRectangularTileStrategy {
    private final ITile[][] tiles;
    private final int boardHeight;
    private final int boardWidth;

    /**
     * Funzione per inserire il tile nella griglia
     * @param tile il pezzo da inserire
     * @throws IllegalStateException se il tile non può essere inserito nella griglia perché uscirebbe da essa.
     */
    @Override
    public void apply(IRectangularTile tile) {
        for(int i = 0; i < tile.getHeight(); i++) {
            for(int j = 0; j < tile.getWidth(); j++) {
                //controlla se il tile esce dalla griglia
                if(i >= boardHeight || j >= boardWidth) {
                    throw new IllegalStateException("Invalid configuration found, unable to generate response");
                }
                //controlla se il tile viene posizionato in un posto già occupato
                if(tiles[i+tile.getY()][j+tile.getX()]!=null){
                    throw new IllegalStateException("Invalid configuration found, unable to generate response");
                }
                //sennò lo inserisce nella griglia
                tiles[i+tile.getY()][j+tile.getX()] = tile;
            }
        }
    }
}
