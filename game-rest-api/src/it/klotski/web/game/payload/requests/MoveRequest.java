package it.klotski.web.game.payload.requests;

import it.klotski.web.game.domain.move.Direction;
import it.klotski.web.game.domain.tile.ITile;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.TreeSet;

/**
 * Classe per la gestione delle richieste di movimento di un tile all'interno della griglia.
 */
@Getter
@RequiredArgsConstructor
public class MoveRequest {
    private final ITile tileToMove;
    private final Direction direction;
    private final long gameId;
    private final TreeSet<ITile> tiles;
}
