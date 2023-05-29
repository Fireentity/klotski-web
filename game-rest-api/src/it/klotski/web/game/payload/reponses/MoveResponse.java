package it.klotski.web.game.payload.reponses;

import it.klotski.web.game.domain.tile.ITile;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.TreeSet;

/**
 * Questa classe rappresenta la risposta di un'operazione di spostamento.
 * La classe `MoveResponse` contiene un insieme di tessere (`tiles`) che rappresentano lo stato aggiornato dopo un'operazione di spostamento.
 * Contiene i seguenti campi:
 * - `winning`: un flag booleano che indica se l'operazione di spostamento ha portato alla vittoria del gioco
 * - `tiles`: l'insieme di tessere che rappresentano lo stato aggiornato dopo un'operazione di spostamento
 * La classe è annotata con `@Getter`, che genera automaticamente i metodi getter per i campi, consentendo di accedere ai valori degli attributi.
 * È anche annotata con `@RequiredArgsConstructor`, che genera automaticamente un costruttore con tutti i campi richiesti come argomenti, semplificando così la creazione di un oggetto `MoveResponse` con i valori desiderati per il flag di vittoria e l'insieme di tessere.
 */
@Getter
@RequiredArgsConstructor
public class MoveResponse {
    /**
     * Un flag booleano che indica se l'operazione di spostamento ha portato alla vittoria del gioco.
     */
    private final boolean winning;

    /**
     * L'insieme di tessere che rappresentano lo stato aggiornato dopo un'operazione di spostamento.
     */
    private final TreeSet<ITile> tiles;
}
