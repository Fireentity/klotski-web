package it.klotski.web.game.domain.tile;

import it.klotski.web.game.domain.move.Direction;
import it.klotski.web.game.domain.tile.visitor.ITileVisitor;

/**
 * Questa classe rappresenta una tessera vincente nel gioco.
 * Estende la classe RectangularTile e implementa l'interfaccia IWinningTile.
 */
public class WinningTile extends RectangularTile implements IWinningTile {

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

    /**
     * Crea un nuovo oggetto WinningTile con un nuovo id.
     *
     * @param id il nuovo id per l'oggetto WinningTile
     * @return un nuovo oggetto WinningTile con l'id specificato
     */
    @Override
    public WinningTile withId(int id) {
        return new WinningTile(id, getX(), getY(), getWidth(), getHeight());
    }

    /**
     * Crea un nuovo oggetto WinningTile con una nuova posizione x.
     *
     * @param x la nuova posizione x per l'oggetto WinningTile
     * @return un nuovo oggetto WinningTile con la posizione x specificata
     */
    @Override
    public WinningTile withX(int x) {
        return new WinningTile(getId(), x, getY(), getWidth(), getHeight());
    }

    /**
     * Crea un nuovo oggetto WinningTile con una nuova posizione y.
     *
     * @param y la nuova posizione y per l'oggetto WinningTile
     * @return un nuovo oggetto WinningTile con la posizione y specificata
     */
    @Override
    public WinningTile withY(int y) {
        return new WinningTile(getId(), getX(), y, getWidth(), getHeight());

    }

    /**
     * Crea un nuovo oggetto WinningTile con una nuova larghezza.
     *
     * @param width la nuova larghezza per l'oggetto WinningTile
     * @return un nuovo oggetto WinningTile con la larghezza specificata
     */
    @Override
    public WinningTile withWidth(int width) {
        return new WinningTile(getId(), getX(), getY(), width, getHeight());
    }

    /**
     * Crea un nuovo oggetto WinningTile con una nuova altezza.
     *
     * @param height la nuova altezza per l'oggetto WinningTile
     * @return un nuovo oggetto WinningTile con l'altezza specificata
     */
    @Override
    public WinningTile withHeight(int height) {
        return new WinningTile(getId(), getX(), getY(), getWidth(), height);
    }

    /**
     * Sposta questa tessera in una direzione specificata.
     *
     * @param direction la direzione in cui spostare questa tessera
     * @return un nuovo oggetto RectangularTile che rappresenta questa tessera dopo essere stata spostata nella direzione specificata
     */
    @Override
    public RectangularTile move(Direction direction) {
        return this.withX(this.getX() + direction.getX()).withY(this.getY() + direction.getY());
    }

    /**
     * Accetta un ITileVisitor per visitare questa tessera.
     *
     * @param visitor un ITileVisitor per visitare questa tessera
     */
    @Override
    public void accept(ITileVisitor visitor) {
        visitor.visitWinningTile(this);
    }
}