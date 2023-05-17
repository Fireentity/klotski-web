package it.klotski.web.game.services.user.strategy;

import it.klotski.web.game.domain.tile.IRectangularTile;

/**
 * Interfaccia che utilizza il design pattern "Strategy" per definire il comportamento associato ai tile rettangolari.
 *
 * L'interfaccia IRectangularTileStrategy definisce il metodo `apply`, che rappresenta l'algoritmo o la funzione
 * specifica da applicare a un tile rettangolare. L'obiettivo è rendere il comportamento dei tile rettangolari
 * intercambiabile, consentendo di utilizzare diverse strategie per l'inserimento e il movimento dei tile.
 */
public interface IRectangularTileStrategy {
    /**
     * Applica la strategia al tile rettangolare specificato.
     *
     * @param tile il tile rettangolare a cui applicare la strategia
     */
    void apply(IRectangularTile tile);
}
