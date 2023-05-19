package it.klotski.web.game.tile;

import it.klotski.web.game.move.Direction;
import it.klotski.web.game.services.visitor.ITileVisitor;


public interface ITile extends Comparable<ITile> {
    int getId();

    void accept(ITileVisitor visitor);

    RectangularTile move(Direction direction);

}
