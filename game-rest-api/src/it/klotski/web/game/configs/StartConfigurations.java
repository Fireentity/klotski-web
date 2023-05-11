package it.klotski.web.game.configs;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class StartConfigurations {
    private final List<Board> configurations;
}
