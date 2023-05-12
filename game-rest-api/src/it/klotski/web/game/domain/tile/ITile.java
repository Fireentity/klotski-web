package it.klotski.web.game.domain.tile;

import it.klotski.web.game.domain.move.Direction;
import it.klotski.web.game.services.user.visitor.ITileVisitor;

public interface ITile extends Comparable<ITile> {
    int getId();

    void accept(ITileVisitor visitor);

    RectangularTile move(Direction direction);

}
