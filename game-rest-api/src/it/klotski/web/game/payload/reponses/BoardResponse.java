package it.klotski.web.game.payload.reponses;


import it.klotski.web.game.tile.ITile;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor

/**
 * Questa classe rappresenta la risposta del tabellone di gioco.
 *
 * La classe BoardResponse contiene una lista di oggetti ITile che rappresentano
 * i tessere del tabellone di gioco.
 */
public class BoardResponse {
    /**
     * La lista delle tessere del tabellone di gioco.
     */
    private final List<ITile> tiles;
}
