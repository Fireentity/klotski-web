package it.klotski.web.game.payload.requests;

import it.klotski.web.game.domain.move.Direction;
import it.klotski.web.game.domain.tile.ITile;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.TreeSet;

/**
 * Classe per la gestione delle richieste di movimento di un tile all'interno della griglia.
 *
 * La classe `MoveRequest` è una classe di richiesta che contiene i campi necessari per eseguire un movimento di un tile all'interno della griglia di gioco.
 * Contiene i seguenti campi:
 * - `tileToMove`: il tile da spostare di tipo `ITile`
 * - `direction`: la direzione del movimento di tipo `Direction`
 * - `gameId`: l'ID del gioco a cui appartiene il tile di tipo `long`
 * - `tiles`: un `TreeSet` di oggetti `ITile` che rappresentano la configurazione dei tile nella griglia
 *
 * La classe è annotata con `@Getter`, che genera automaticamente i metodi getter per i campi, consentendo di accedere ai valori degli attributi.
 * È anche annotata con `@RequiredArgsConstructor`, che genera automaticamente un costruttore con tutti i campi richiesti come argomenti, semplificando così la creazione di un oggetto `MoveRequest` con i valori desiderati per il tile, la direzione, l'ID del gioco e la configurazione dei tile.
 */
@Getter
@RequiredArgsConstructor
public class MoveRequest {
    private final ITile tileToMove;
    private final Direction direction;
    private final long gameId;
    private final TreeSet<ITile> tiles;
}
