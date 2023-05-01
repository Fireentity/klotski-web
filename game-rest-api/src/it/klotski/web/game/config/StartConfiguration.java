package it.klotski.web.game.config;

import java.util.List;

public record StartConfiguration(int id, List<Tile> tiles) {
}
