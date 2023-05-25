package it.klotski.solutions.configurer;

/**
 * Questa classe rappresenta una tessera vincente nel gioco.
 * Estende la classe RectangularTile e implementa l'interfaccia IWinningTile.
 */
public class WinningTile extends RectangularTile {

    /**
     * Costruttore per la classe WinningTile.
     *
     * @param id     l'id della tessera
     * @param x      la posizione x della tessera
     * @param y      la posizione y della tessera
     * @param width  la larghezza della tessera
     * @param height l'altezza della tessera
     */
    public WinningTile(int id, int x, int y, int width, int height) {
        super(id, x, y, width, height);
    }

}