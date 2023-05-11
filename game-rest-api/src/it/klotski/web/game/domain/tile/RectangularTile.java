package it.klotski.web.game.domain.tile;

import it.klotski.web.game.domain.move.Direction;
import it.klotski.web.game.services.user.visitor.ITileVisitor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.With;

@Getter
@With
@RequiredArgsConstructor
public class RectangularTile implements IRectangularTile {
    private final int id;
    private final int x;
    private final int y;
    private final int width;
    private final int height;

    @Override
    public void accept(ITileVisitor visitor) {
        visitor.visitRectangularTile(this);
    }

    @Override
    public RectangularTile move(Direction direction) {
        return this.withX(this.x + direction.getX()).withY(this.y + direction.getY());
    }

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

    @Override
    public int hashCode() {
        int result = getId();
        result = 31 * result + getX();
        result = 31 * result + getY();
        result = 31 * result + getWidth();
        result = 31 * result + getHeight();
        return result;
    }

    @Override
    public int compareTo(ITile o) {
        return Integer.compare(id,o.getId());
    }
}
