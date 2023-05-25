package it.klotski.web.game.domain.move;

import lombok.Getter;
/**
 * Enumerazione per le possibili direzioni di movimento dei tiles: destra, sinistra, sopra, sotto.
 */
@Getter
public enum Direction {
    RIGHT(1, 0), LEFT(-1, 0), TOP(0, -1), DOWN(0, 1);

    /**
     * Costruttore della classe Direction.
     * @param x la coordinata x.
     * @param y la coordinata y.
     */
    Direction(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * La coordinata x.
     */
    private final int x;

    /**
     * La coordinata y.
     */
    private final int y;

    public static Direction opposite(Direction direction) {
        switch (direction) {
            case TOP -> {
                return DOWN;
            }
            case DOWN -> {
                return TOP;
            }
            case LEFT -> {
                return RIGHT;
            }
            case RIGHT -> {
                return LEFT;
            }
            default -> throw new IllegalStateException(String.format("Unable to find opposite direction for direction %s", direction.name()));
        }
    }
}