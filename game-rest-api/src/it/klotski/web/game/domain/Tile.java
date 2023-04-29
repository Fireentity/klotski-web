package it.klotski.web.game.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class Tile {
    private final int id;
    private final int x;
    private final int y;
    private final int width;
    private final int length;
}
