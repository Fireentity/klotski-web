package it.klotski.web.game.controllers;

import it.klotski.web.game.configs.Board;
import it.klotski.web.game.domain.game.GameView;
import it.klotski.web.game.domain.user.User;
import it.klotski.web.game.exceptions.GameNotFoundException;
import it.klotski.web.game.payload.reponses.GameResponse;
import it.klotski.web.game.payload.requests.GameRequest;
import it.klotski.web.game.services.IPuzzleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * Il controller che gestisce le richieste relative ai giochi.
 * Fornisce endpoint per creare un nuovo gioco, ottenere i dettagli di un gioco specifico
 * e recuperare l'ultimo gioco non completato dell'utente autenticato.
 */

@RestController
@RequestMapping(path = "/api/games")
public class GameController {
    private final IPuzzleService puzzleService;

    /**
     * Costruisce un'istanza di GameController con il servizio di puzzle specificato.
     *
     * @param puzzleService il servizio di puzzle utilizzato per gestire le operazioni di gioco
     */
    @Autowired
    public GameController(IPuzzleService puzzleService) {
        this.puzzleService = puzzleService;
    }

    /**
     * Crea un nuovo gioco a partire da una configurazione d'inizio specificata.
     *
     * @param gameRequest la richiesta di gioco contenente l'identificatore della configurazione d'inizio
     * @return una ResponseEntity contenente il GameResponse con i dettagli del gioco creato
     */
    @PostMapping
    public ResponseEntity<GameResponse> createGame(@RequestBody GameRequest gameRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return ResponseEntity.ok(puzzleService.createGameFromConfiguration(authentication.getName(), gameRequest.getStartConfigId()));
    }

    /**
     * Restituisce i dettagli di un gioco specificato dall'identificatore del gioco.
     *
     * @param gameId l'identificatore del gioco
     * @return una ResponseEntity contenente il GameResponse con i dettagli del gioco richiesto
     * @throws GameNotFoundException se il gioco non viene trovato
     */
    @GetMapping
    public ResponseEntity<GameResponse> getGame(@RequestParam long gameId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        GameView game = puzzleService.findGameViewById(gameId).orElseThrow(GameNotFoundException::new);
        if (game.getPlayer().getId() != user.getId()) {
            throw new GameNotFoundException();
        }

        Board board = puzzleService.calculateCurrentConfiguration(game);
        return ResponseEntity.ok(GameResponse.from(game, board));
    }

    /**
     * Restituisce i dettagli dell'ultimo gioco non completato dell'utente autenticato.
     *
     * @return una ResponseEntity contenente il GameResponse con i dettagli dell'ultimo gioco non completato
     */
    @GetMapping(path = "/unfinished")
    public ResponseEntity<GameResponse> getLastUnfinishedGame() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Optional<GameView> gameOptional = puzzleService.findLastUnfinishedGame(authentication.getName());
        if (gameOptional.isEmpty()) {
            return ResponseEntity.ok().build();
        }

        GameView game = gameOptional.get();
        Board board = puzzleService.calculateCurrentConfiguration(game);

        return ResponseEntity.ok(GameResponse.from(game, board));
    }
}
