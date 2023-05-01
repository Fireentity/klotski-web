package it.klotski.web.game.config;

import it.klotski.web.game.payload.requests.TileRequest;

public record Tile(int id, int x, int y, int width, int height) {
    public static Tile from(TileRequest request) {
        return new Tile(request.id(), request.x(), request.y(), request.width(), request.height());
    }
}
