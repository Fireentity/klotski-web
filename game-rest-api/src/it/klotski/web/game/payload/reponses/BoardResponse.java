package it.klotski.web.game.payload.reponses;

import java.util.List;

public record BoardResponse(List<TileResponse> tiles) {
}
