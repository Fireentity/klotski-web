package it.klotski.web.game.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class Move {
    private final int id;
    private final Tile tile;
    private final Direction direction;
}
