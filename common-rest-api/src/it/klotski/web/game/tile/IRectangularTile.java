package it.klotski.web.game.tile;

/**
 * Questa interfaccia rappresenta una tessera rettangolare.
 */
public interface IRectangularTile extends ITile {
    /**
     * Restituisce l'ID della tessera.
     *
     * @return l'ID della tessera.
     */
    int getId();

    /**
     * Restituisce la coordinata X della tessera.
     *
     * @return la coordinata X della tessera.
     */
    int getX();

    /**
     * Restituisce la coordinata Y della tessera.
     *
     * @return la coordinata Y della tessera.
     */
    int getY();

    /**
     * Restituisce la larghezza della tessera.
     *
     * @return la larghezza della tessera.
     */
    int getWidth();

    /**
     * Restituisce l'altezza della tessera.
     *
     * @return l'altezza della tessera.
     */
    int getHeight();
}