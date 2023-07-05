package it.klotski.web.game.payload.requests;

import it.klotski.web.game.domain.tile.ITile;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.TreeSet;

/**
 * Classe di richiesta per la risoluzione del gioco.
 * La classe `SolveRequest` rappresenta una richiesta di risoluzione del gioco.
 * Contiene l'insieme delle tessere attuali del gioco.
 */
@Getter
@RequiredArgsConstructor
@EqualsAndHashCode
public class SolveRequest {
    /**
     * L'insieme delle tessere attuali del gioco.
     */
    private final TreeSet<ITile> tiles;
}
