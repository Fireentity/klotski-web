package it.klotski.web.game.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class Game {
    private final int id;
    private final long duration;
    private final long date;
    private final List<Move> moves;
}
