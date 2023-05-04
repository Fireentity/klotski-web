package it.klotski.web.game.domain.tile;

import it.klotski.web.game.services.user.visitor.ITileVisitor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class RectangularTile implements IRectangularTile {
    private final int id;
    private final int x;
    private final int y;
    private final int width;
    private final int height;

    @Override
    public void accept(ITileVisitor visitor) {
        visitor.visit(this);
    }
}
