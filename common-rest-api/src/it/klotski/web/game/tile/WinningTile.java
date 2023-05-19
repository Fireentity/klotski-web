package it.klotski.web.game.tile;

import it.klotski.web.game.move.Direction;
import it.klotski.web.game.services.visitor.ITileVisitor;

public class WinningTile extends RectangularTile implements IWinningTile {

    public WinningTile(int id, int x, int y, int width, int height) {
        super(id, x, y, width, height);
    }

    @Override
    public WinningTile withId(int id) {
        return new WinningTile(id, getX(), getY(), getWidth(), getHeight());
    }

    @Override
    public WinningTile withX(int x) {
        return new WinningTile(getId(), x, getY(), getWidth(), getHeight());
    }

    @Override
    public WinningTile withY(int y) {
        return new WinningTile(getId(), getX(), y, getWidth(), getHeight());

    }

    @Override
    public WinningTile withWidth(int width) {
        return new WinningTile(getId(), getX(), getY(), width, getHeight());
    }

    @Override
    public WinningTile withHeight(int height) {
        return new WinningTile(getId(), getX(), getY(), getWidth(), height);
    }

    @Override
    public RectangularTile move(Direction direction) {
        return this.withX(this.getX() + direction.getX()).withY(this.getY() + direction.getY());
    }

    @Override
    public void accept(ITileVisitor visitor) {
        visitor.visitWinningTile(this);
    }
}
