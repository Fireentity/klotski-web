package it.klotski.web.game.payload.requests;

import it.klotski.web.game.domain.Direction;
import lombok.Getter;

@Getter
public class MoveRequest {
    private TileRequest tile;
    private Direction direction;
    private long gameId;
}
