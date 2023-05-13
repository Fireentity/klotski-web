package it.klotski.web.game.payload.requests;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Classe per la gestione delle richieste di creazione di una nuova partita.
 */
@Getter
@RequiredArgsConstructor
public class GameRequest {
    private final int startConfigId;
}
