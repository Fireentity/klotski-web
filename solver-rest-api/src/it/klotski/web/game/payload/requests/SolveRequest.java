package it.klotski.web.game.payload.requests;

import it.klotski.web.game.move.Direction;
import it.klotski.web.game.tile.ITile;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.TreeSet;

@Getter
@RequiredArgsConstructor
public class SolveRequest {
    private final ITile tileToMove;
    private final Direction direction;
    private final long gameId;
    private final TreeSet<ITile> tiles;
}
