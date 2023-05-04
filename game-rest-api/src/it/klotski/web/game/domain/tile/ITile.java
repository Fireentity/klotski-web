package it.klotski.web.game.domain.tile;

import it.klotski.web.game.services.user.visitor.ITileVisitor;

public interface ITile {
    int getId();
    void accept(ITileVisitor visitor);

}
