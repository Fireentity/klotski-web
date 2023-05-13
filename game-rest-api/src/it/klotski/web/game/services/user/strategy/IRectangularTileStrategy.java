package it.klotski.web.game.services.user.strategy;

import it.klotski.web.game.domain.tile.IRectangularTile;

/**
 * Interfaccia che utilizza il design pattern "Strategy" che consiste nel creare una famiglia di algoritmi e funzioni,
 * e incapsularli per renderli intercambiabili, in questo caso i diversi comportamenti associati ai tiles come
 * l'inserimento e il movimento di essi.
 */
public interface IRectangularTileStrategy {
    void apply(IRectangularTile tile);
}
