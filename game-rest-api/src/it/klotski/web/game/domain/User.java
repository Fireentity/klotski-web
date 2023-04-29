package it.klotski.web.game.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class User {
    private final int id;
    private final String name;
    private final String email;
    private final List<Game> games;
}
