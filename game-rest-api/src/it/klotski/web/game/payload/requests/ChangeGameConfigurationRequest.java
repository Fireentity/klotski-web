package it.klotski.web.game.payload.requests;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Classe per la gestione delle richieste di cambio configurazione di un gioco.
 * La classe `ChangeGameConfigurationRequest` è una classe di richiesta che contiene i campi necessari per cambiare la configurazione di un gioco.
 * Contiene i seguenti campi:
 * - `gameId`: l'ID del gioco da modificare di tipo `long`
 * - `startConfigurationId`: l'ID della nuova configurazione di inizio del gioco di tipo `int`
 * La classe è annotata con `@Getter`, che genera automaticamente i metodi getter per i campi, consentendo di accedere ai valori degli attributi.
 * È anche annotata con `@RequiredArgsConstructor`, che genera automaticamente un costruttore con tutti i campi richiesti come argomenti, semplificando così la creazione di un oggetto `ChangeGameConfigurationRequest` con i valori desiderati per l'ID del gioco e l'ID della nuova configurazione di inizio.
 */
@Getter
@RequiredArgsConstructor
public class ChangeGameConfigurationRequest {
    /**
     * L'ID del gioco da modificare.
     */
    private final long gameId;

    /**
     * L'ID della nuova configurazione di inizio del gioco.
     */
    private final int startConfigurationId;
}
