package it.klotski.web.game.payload.reponses;

import it.klotski.web.game.domain.tile.ITile;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class BoardResponse {
    private final List<ITile> tiles;
}
