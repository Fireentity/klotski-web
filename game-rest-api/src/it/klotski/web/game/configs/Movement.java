package it.klotski.web.game.configs;

import it.klotski.web.game.domain.move.Direction;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class Movement {
    private final int x;
    private final int y;
    private final Direction direction;
}
