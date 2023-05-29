package it.klotski.web.game.configs;

import it.klotski.web.game.domain.move.Direction;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Classe per la configurazione di un movimento.
 * La classe `Movement` rappresenta la configurazione di un movimento all'interno della griglia di gioco.
 * Contiene i seguenti campi:
 * - `x`: la coordinata x in alto a sinistra del pezzo da muovere di tipo `int`
 * - `y`: la coordinata y in alto a sinistra del pezzo da muovere di tipo `int`
 * - `direction`: la direzione del movimento di tipo `Direction`
 * La classe è annotata con `@Getter`, che genera automaticamente i metodi getter per i campi, consentendo di accedere ai valori degli attributi.
 * È anche annotata con `@RequiredArgsConstructor`, che genera automaticamente un costruttore con tutti i campi richiesti come argomenti, semplificando così la creazione di un oggetto `Movement` con i valori desiderati per le coordinate e la direzione del movimento.
 */
@Getter
@RequiredArgsConstructor
public class Movement {
    /**
     * La coordinata x in alto a sinistra del pezzo da muovere.
     */
    private final int x;

    /**
     * La coordinata y in alto a sinistra del pezzo da muovere.
     */
    private final int y;

    /**
     * La direzione del movimento.
     */
    private final Direction direction;
}
