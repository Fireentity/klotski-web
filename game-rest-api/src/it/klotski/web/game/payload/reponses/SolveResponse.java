package it.klotski.web.game.payload.reponses;

import it.klotski.web.game.configs.Movement;
import it.klotski.web.game.domain.move.Direction;
import it.klotski.web.game.domain.tile.ITile;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class SolveResponse {
    private final ITile tile;
    private final Direction direction;

    public static SolveResponse from(ITile tile, Movement movement) {
        return new SolveResponse(tile, movement.getDirection());
    }
}