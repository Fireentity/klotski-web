package it.klotski.web.game.controllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import it.klotski.web.game.configs.Movement;
import it.klotski.web.game.domain.tile.ITile;
import it.klotski.web.game.domain.tile.strategy.RectangularTileSearchStrategy;
import it.klotski.web.game.domain.tile.strategy.TileFieldExclusionStrategy;
import it.klotski.web.game.domain.tile.visitor.ITileVisitor;
import it.klotski.web.game.domain.tile.visitor.RectangularTileVisitor;
import it.klotski.web.game.domain.tile.visitor.WinningTileVisitor;
import it.klotski.web.game.exceptions.SolutionNotFoundException;
import it.klotski.web.game.exceptions.TileNotFoundException;
import it.klotski.web.game.payload.reponses.SolveResponse;
import it.klotski.web.game.payload.requests.SolveRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;

/**
 * Controller per la risoluzione del gioco.
 * La classe `SolveController` gestisce le richieste per la risoluzione del gioco,
 * determinando la prossima mossa migliore in base alla configurazione del tabellone di gioco fornita.
 * Utilizza un'istanza di `HashMap` per memorizzare le soluzioni del gioco in base all'hash del tabellone di gioco.
 * La classe è annotata con `@RestController` per indicare che è un controller REST,
 * e con `@RequestMapping` per specificare il percorso di base per tutte le richieste gestite dal controller.
 */
@RestController
@RequestMapping(path = "/api/solver")
public class SolveController {

    private final HashMap<String, Movement> solutions;

    /**
     * Costruttore del controller SolveController.
     *
     * @param solutions L'oggetto HashMap contenente le soluzioni del gioco.
     */
    @Autowired
    public SolveController(HashMap<String, Movement> solutions) {
        this.solutions = solutions;
    }

    /**
     * Gestisce la richiesta per la prossima mossa migliore.
     *
     * La funzione `nextBestMove` gestisce la richiesta POST per determinare la prossima mossa migliore del gioco.
     * Prende in input un oggetto `SolveRequest` che contiene la configurazione attuale del tabellone di gioco.
     * Utilizza un'istanza di `Gson` per escludere il campo "id" durante la serializzazione dell'oggetto `SolveRequest` in JSON.
     * Calcola l'hash del tabellone di gioco utilizzando l'algoritmo MD5 e lo utilizza per recuperare la prossima mossa migliore dalla mappa delle soluzioni.
     * Se la mossa non viene trovata, viene lanciata un'eccezione `SolutionNotFoundException`.
     * Applica i visitatori alle tessere del tabellone di gioco per identificare la tessera rettangolare in movimento e verificare se è la tessera di vittoria.
     * Restituisce una risposta `SolveResponse` contenente la tessera in movimento e la direzione della prossima mossa migliore.
     *
     * @param solveRequest L'oggetto `SolveRequest` che contiene la configurazione attuale del tabellone di gioco.
     * @return Una risposta `ResponseEntity` contenente l'oggetto `SolveResponse` con la tessera in movimento e la direzione della prossima mossa migliore.
     */
    @PostMapping
    public ResponseEntity<SolveResponse> nextBestMove(@RequestBody SolveRequest solveRequest) {
        String fieldNameToExclude = "id";
        Gson gson = new GsonBuilder()
                .setExclusionStrategies(new TileFieldExclusionStrategy(fieldNameToExclude))
                .create();
        String boardHash = DigestUtils.md5DigestAsHex(gson.toJson(solveRequest.getTiles()).getBytes(StandardCharsets.UTF_8));
        Movement movement = solutions.get(boardHash);
        if (movement == null) {
            throw new SolutionNotFoundException();
        }
        RectangularTileSearchStrategy rectangularTileStrategy = new RectangularTileSearchStrategy(movement.getX(), movement.getY());
        List<ITileVisitor> visitors = List.of(new RectangularTileVisitor(rectangularTileStrategy),
                new WinningTileVisitor(rectangularTileStrategy));
        for (ITile tile : solveRequest.getTiles()) {
            visitors.forEach(tile::accept);
        }
        ITile tile = rectangularTileStrategy.getTile().orElseThrow(TileNotFoundException::new);
        return ResponseEntity.ok(SolveResponse.from(tile, movement));
    }
}
