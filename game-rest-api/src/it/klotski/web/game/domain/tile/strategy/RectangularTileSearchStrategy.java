package it.klotski.web.game.domain.tile.strategy;

import it.klotski.web.game.domain.tile.IRectangularTile;
import it.klotski.web.game.domain.tile.ITile;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

/**
 * Classe che implementa la strategia di ricerca di una tessera rettangolare.
 * Implementa l'interfaccia {@link IRectangularTileSearchStrategy}.
 */
@RequiredArgsConstructor
public class RectangularTileSearchStrategy implements IRectangularTileSearchStrategy {
    private ITile tile;
    private final int x;
    private final int y;

    /**
     * Applica la strategia di ricerca alla tessera rettangolare specificata.
     * Se le coordinate della tessera corrispondono alle coordinate di ricerca,
     * imposta la tessera corrente come risultato della ricerca.
     *
     * @param tile La tessera rettangolare su cui applicare la strategia di ricerca.
     */
    @Override
    public void apply(IRectangularTile tile) {
        if (tile.getX() == x && tile.getY() == y) {
            this.tile = tile;
        }
    }

    /**
     * Restituisce la tessera trovata dalla strategia di ricerca, se presente.
     *
     * @return Un'istanza di {@link Optional} contenente la tessera trovata, o un'istanza vuota se la tessera non è stata trovata.
     */
    public Optional<ITile> getTile() {
        return Optional.ofNullable(tile);
    }
}
