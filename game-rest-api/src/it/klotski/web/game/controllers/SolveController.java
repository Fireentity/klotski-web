package it.klotski.web.game.controllers;

import it.klotski.web.game.payload.reponses.SolveResponse;
import it.klotski.web.game.payload.requests.SolveRequest;
import it.klotski.web.game.services.ISolverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    private final ISolverService solverService;

    @Autowired
    public SolveController(ISolverService solverService) {
        this.solverService = solverService;
    }

    /**
     * Gestisce la richiesta per la prossima mossa migliore.
     * <p>
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
        return ResponseEntity.ok(solverService.nextBestMove(solveRequest));
    }
}
