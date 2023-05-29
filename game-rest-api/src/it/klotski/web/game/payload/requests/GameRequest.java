package it.klotski.web.game.payload.requests;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Classe per la gestione delle richieste di creazione di una nuova partita.
 * La classe `GameRequest` è una classe di richiesta utilizzata per gestire le richieste di creazione di una nuova partita.
 * Contiene il seguente campo:
 * - `startConfigId`: l'ID della configurazione di partenza di tipo `int`
 * La classe è annotata con `@Getter`, che genera automaticamente i metodi getter per i campi, consentendo di accedere al valore dell'attributo.
 * È anche annotata con `@RequiredArgsConstructor`, che genera automaticamente un costruttore con il campo richiesto come argomento, semplificando così la creazione di un oggetto `GameRequest` con il valore desiderato per l'ID della configurazione di partenza.
 */
@Getter
@RequiredArgsConstructor
public class GameRequest {
    /**
     * L'ID della configurazione di partenza.
     */
    private final int startConfigId;
}
