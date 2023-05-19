package it.klotski.web.game.payload.reponses;

import it.klotski.web.game.tile.ITile;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.TreeSet;

@Getter
@RequiredArgsConstructor
public class MoveResponse {
    private final TreeSet<ITile> tiles;

}
