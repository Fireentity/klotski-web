package it.klotski.web.game.payload.reponses;

import it.klotski.web.game.configs.Movement;
import it.klotski.web.game.domain.move.Direction;
import it.klotski.web.game.domain.tile.ITile;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Classe per la gestione delle risposte di risoluzione del gioco.
 * La classe `SolveResponse` è una classe di risposta che contiene i campi necessari per la risoluzione del gioco.
 * Contiene i seguenti campi:
 * - `tile`: il tile risolto di tipo `ITile`
 * - `direction`: la direzione di risoluzione di tipo `Direction`
 * La classe è annotata con `@Getter`, che genera automaticamente i metodi getter per i campi, consentendo di accedere ai valori degli attributi.
 * È anche annotata con `@RequiredArgsConstructor`, che genera automaticamente un costruttore con tutti i campi richiesti come argomenti, semplificando così la creazione di un oggetto `SolveResponse` con i valori desiderati per il tile e la direzione di risoluzione.
 * La classe fornisce un metodo statico `from` che permette di creare un oggetto `SolveResponse` a partire da un oggetto `ITile` e un oggetto `Movement`. Il metodo utilizza la direzione dell'oggetto `Movement` per impostare il campo `direction` dell'oggetto `SolveResponse`.
 */
@Getter
@RequiredArgsConstructor
public class SolveResponse {
    /**
     * Il tile risolto.
     */
    private final ITile tile;

    /**
     * La direzione di risoluzione.
     */
    private final Direction direction;

    /**
     * Metodo statico per creare un oggetto `SolveResponse` a partire da un oggetto `ITile` e un oggetto `Movement`.
     * Utilizza la direzione dell'oggetto `Movement` per impostare il campo `direction` dell'oggetto `SolveResponse`.
     *
     * @param tile il tile risolto
     * @param movement l'oggetto `Movement` che contiene la direzione di risoluzione
     * @return un'istanza di `SolveResponse` con i valori specificati
     */
    public static SolveResponse from(ITile tile, Movement movement) {
        return new SolveResponse(tile, movement.getDirection());
    }
}
