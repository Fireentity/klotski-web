package it.klotski.web.game.payload.responses;

import it.klotski.web.game.domain.move.Direction;
import it.klotski.web.game.domain.tile.ITile;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.TreeSet;

@Getter
@RequiredArgsConstructor
public class MoveResponse {
    private final int tileId;
    private final Direction direction;
}