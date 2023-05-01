package it.klotski.web.game.payload.reponses;

import it.klotski.web.game.config.Tile;

public record TileResponse(long id, int x, int y, int width, int height) {
    public static TileResponse from(Tile tile) {
        return new TileResponse(tile.id(), tile.x(), tile.y(), tile.width(), tile.height());
    }
}
