package it.klotski.web.game.payload.requests;

import it.klotski.web.game.domain.move.Direction;
import it.klotski.web.game.domain.tile.ITile;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class MoveRequest {
    private final ITile tile;
    private final Direction direction;
    private final long gameId;
    private final BoardRequest boardRequest;
}
