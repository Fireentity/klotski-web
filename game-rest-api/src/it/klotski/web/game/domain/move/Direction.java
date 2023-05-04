package it.klotski.web.game.domain.move;

import lombok.Getter;

@Getter
public enum Direction {
    //TODO try to fix that top and down vectors are inverted
    RIGHT(1, 0), LEFT(-1, 0), TOP(0, -1), DOWN(1, 0);

    private final int x;
    private final int y;

    Direction(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
