package it.klotski.web.game.payload.reponses;

import it.klotski.web.game.domain.tile.ITile;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.TreeSet;

@Getter
@RequiredArgsConstructor

/**
 * Questa classe rappresenta la risposta di un'operazione di spostamento.
 *
 * La classe MoveResponse contiene un insieme di tessere (tiles) che rappresentano
 * lo stato aggiornato dopo un'operazione di spostamento.
 */
public class MoveResponse {
    /**
     * L'insieme di tessere che rappresentano lo stato aggiornato dopo un'operazione di spostamento.
     */
    private final TreeSet<ITile> tiles;
}
