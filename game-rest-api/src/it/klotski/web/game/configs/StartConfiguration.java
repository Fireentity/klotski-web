package it.klotski.web.game.configs;

import it.klotski.web.game.domain.tile.ITile;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class StartConfiguration {
    private final int boardWidth;
    private final int boardHeight;
    private final int id;
    private final List<ITile> tiles;
}
