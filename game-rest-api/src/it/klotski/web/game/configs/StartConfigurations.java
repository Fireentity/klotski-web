package it.klotski.web.game.configs;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

/**
 * Rappresenta le configurazioni di partenza del gioco.
 */
@Getter
@RequiredArgsConstructor
public class StartConfigurations {
    /**
     * Lista delle configurazioni di tabelloni di partenza.
     */
    private final List<Board> configurations;
}
