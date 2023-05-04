package it.klotski.web.game.domain.tile;

public interface IRectangularTile extends ITile {
    int getId();

    int getX();

    int getY();

    int getWidth();

    int getHeight();
}
