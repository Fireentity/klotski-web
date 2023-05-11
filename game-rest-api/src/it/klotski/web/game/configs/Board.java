package it.klotski.web.game.configs;

import it.klotski.web.game.domain.tile.ITile;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.With;

import java.util.TreeSet;

@With
@Getter
@RequiredArgsConstructor
public class Board {
    private final int boardWidth;
    private final int boardHeight;
    private final int id;
    private final TreeSet<ITile> tiles;
}
