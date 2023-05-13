package it.klotski.web.game.domain.move;

import lombok.Getter;

/**
 * Enumerazione per le possibili direzioni di movimento dei tiles: destra, sinistra, sopra, sotto.
 */
@Getter
public enum Direction {
    RIGHT(1, 0), LEFT(-1, 0), TOP(0, -1), DOWN(0, 1);

    private final int x;
    private final int y;

    Direction(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
