package it.klotski.web.game.configs;

import it.klotski.web.game.domain.tile.ITile;
import lombok.EqualsAndHashCode;
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
@EqualsAndHashCode
public class Board {
    /**
     * La coordinata x in alto a sinistra del pezzo "winning tile" che determina la vittoria della partita.
     */
    private final int winningX;

    /**
     * La coordinata y in alto a sinistra del pezzo "winning tile" che determina la vittoria della partita.
     */
    private final int winningY;

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
