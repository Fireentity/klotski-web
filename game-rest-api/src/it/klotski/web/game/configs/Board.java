package it.klotski.web.game.configs;


import it.klotski.web.game.domain.tile.ITile;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.With;

import java.util.TreeSet;

/**
 * Rappresenta la griglia di gioco.
 */
@With
@Getter
@RequiredArgsConstructor
public class Board {

    /**
     * Larghezza del tabellone.
     */
    private final int boardWidth;

    /**
     * Altezza del tabellone.
     */
    private final int boardHeight;

    /**
     * ID del tabellone.
     */
    private final int id;

    /**
     * Insieme ordinato di tessere (tiles) presenti nel tabellone.
     */
    private final TreeSet<ITile> tiles;
}
