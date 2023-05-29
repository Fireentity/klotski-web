package it.klotski.web.game.domain.tile.strategy;

import it.klotski.web.game.domain.tile.IRectangularTile;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Strategia per la condizione di vittoria del gioco.
 * La classe `WinConditionStrategy` implementa l'interfaccia `IRectangularTileStrategy`
 * ed è utilizzata per determinare se una determinata tessera ha raggiunto la posizione di vittoria.
 * La classe contiene i seguenti campi:
 * - `winningX`: la coordinata X della posizione di vittoria
 * - `winningY`: la coordinata Y della posizione di vittoria
 * - `winning`: un flag booleano che indica se la condizione di vittoria è stata soddisfatta o meno
 * La classe è annotata con `@Getter` per generare automaticamente i metodi getter per i campi,
 * consentendo di accedere ai valori degli attributi.
 * È anche annotata con `@RequiredArgsConstructor` per generare automaticamente un costruttore con tutti i campi richiesti come argomenti,
 * semplificando così la creazione di un oggetto `WinConditionStrategy` con i valori desiderati per le coordinate di vittoria.
 */
@Getter
@RequiredArgsConstructor
public class WinConditionStrategy implements IRectangularTileStrategy {
    private final int winningX;
    private final int winningY;
    private boolean winning = false;

    /**
     * Applica la strategia di condizione di vittoria a una tessera rettangolare.
     * Se la tessera si trova nella posizione di vittoria, imposta il flag `winning` su true.
     *
     * @param tile La tessera rettangolare a cui applicare la strategia di condizione di vittoria.
     */
    @Override
    public void apply(IRectangularTile tile) {
        if (tile.getX() == winningX && tile.getY() == winningY) {
            winning = true;
        }
    }
}
