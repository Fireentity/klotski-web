package it.klotski.web.game.payload.responses;

import it.klotski.web.game.move.Direction;
import it.klotski.web.game.tile.ITile;
import it.klotski.web.game.utils.Movement;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class MoveResponse {
    private final int tileId;
    private final Direction direction;

    public static MoveResponse from(ITile tile, Movement movement) {
        return new MoveResponse(tile.getId(), movement.getDirection());
    }
}