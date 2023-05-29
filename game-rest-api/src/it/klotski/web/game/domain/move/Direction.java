package it.klotski.web.game.domain.move;

import lombok.Getter;

/**
 * Enumerazione per le possibili direzioni di movimento dei tiles: destra, sinistra, sopra, sotto.
 */
@Getter
public enum Direction {
    RIGHT(1, 0),
    LEFT(-1, 0),
    TOP(0, -1),
    DOWN(0, 1);

    /**
     * Costruttore della classe Direction.
     *
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

    /**
     * Restituisce la direzione opposta a quella specificata.
     *
     * @param direction la direzione di cui si vuole ottenere l'opposta.
     * @return la direzione opposta.
     * @throws IllegalStateException se la direzione specificata non ha un'opposta definita.
     */
    public static Direction opposite(Direction direction) {
        return switch (direction) {
            case TOP -> DOWN;
            case DOWN -> TOP;
            case LEFT -> RIGHT;
            case RIGHT -> LEFT;
            default ->
                    throw new IllegalStateException("Unable to find opposite direction for direction " + direction.name());
        };
    }
}
