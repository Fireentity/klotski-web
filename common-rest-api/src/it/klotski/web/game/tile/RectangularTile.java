package it.klotski.web.game.tile;

import it.klotski.web.game.move.Direction;
import it.klotski.web.game.services.visitor.ITileVisitor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.With;

@Getter
@With
@RequiredArgsConstructor

/**
 * Rappresenta una tessera rettangolare nel gioco Klotski.
 *
 * La classe RectangularTile implementa l'interfaccia IRectangularTile e rappresenta una tessera rettangolare
 * nel gioco Klotski. Contiene informazioni come l'identificatore, le coordinate x e y del punto iniziale
 * della tessera, la larghezza e l'altezza del rettangolo.
 */
public class RectangularTile implements IRectangularTile {
    /**
     * L'identificatore univoco della tessera rettangolare.
     */
    private final int id;

    /**
     * La coordinata x del punto iniziale della tessera rettangolare.
     */
    private final int x;

    /**
     * La coordinata y del punto iniziale della tessera rettangolare.
     */
    private final int y;

    /**
     * La larghezza della tessera rettangolare.
     */
    private final int width;

    /**
     * L'altezza della tessera rettangolare.
     */
    private final int height;

    /**
     * Permette a un visitatore di visitare la tessera rettangolare.
     *
     * @param visitor il visitatore che visita la tessera rettangolare
     */
    @Override
    public void accept(ITileVisitor visitor) {
        visitor.visitRectangularTile(this);
    }

    /**
     * Sposta la tessera rettangolare in una direzione specificata.
     *
     * @param direction la direzione in cui spostare la tessera
     * @return la tessera rettangolare spostata nella direzione specificata
     */
    @Override
    public RectangularTile move(Direction direction) {
        return this.withX(this.x + direction.getX()).withY(this.y + direction.getY());
    }

    /**
     * Confronta se l'oggetto specificato è uguale a questa tessera rettangolare.
     *
     * @param o l'oggetto da confrontare
     * @return true se l'oggetto è uguale a questa tessera rettangolare, false altrimenti
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RectangularTile that = (RectangularTile) o;

        if (getId() != that.getId()) return false;
        if (getX() != that.getX()) return false;
        if (getY() != that.getY()) return false;
        if (getWidth() != that.getWidth()) return false;
        return getHeight() == that.getHeight();
    }

    /**
     * Restituisce il valore hash code della tessera rettangolare.
     *
     * @return il valore hash code
     */
    @Override
    public int hashCode() {
        int result = getId();
        result = 31 * result + getX();
        result = 31 * result + getY();
        result = 31 * result + getWidth();
        result = 31 * result + getHeight();
        return result;
    }

    /**
     * Confronta questa tessera rettangolare con un'altra tessera e restituisce un valore negativo,
     * zero o positivo a seconda che l'identificatore di questa tessera sia minore, uguale o maggiore
     * dell'identificatore dell'altra tessera.
     *
     * @param o l'altra tessera con cui confrontare
     * @return un valore negativo, zero o positivo
     */
    @Override
    public int compareTo(ITile o) {
        return Integer.compare(id,o.getId());
    }
}
