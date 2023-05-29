package it.klotski.web.game.payload.requests;

import it.klotski.web.game.domain.tile.ITile;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.TreeSet;

/**
 * Classe di richiesta per l'annullamento di una mossa.
 * La classe `UndoRequest` rappresenta una richiesta di annullamento di una mossa in una partita.
 * Contiene l'ID della partita e l'insieme delle tessere attuali.
 */
@Getter
@RequiredArgsConstructor
public class UndoRequest {
    /**
     * L'ID della partita in cui si desidera annullare una mossa.
     */
    private final long gameId;

    /**
     * L'insieme delle tessere attuali.
     */
    private final TreeSet<ITile> tiles;
}
