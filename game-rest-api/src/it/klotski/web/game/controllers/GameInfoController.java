package it.klotski.web.game.controllers;

import it.klotski.web.game.domain.game.GameView;
import it.klotski.web.game.exceptions.GameNotFoundException;
import it.klotski.web.game.payload.reponses.GameInfoResponse;
import it.klotski.web.game.services.IPuzzleService;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Il controller che gestisce le richieste relative alle informazioni dei giochi dell'utente.
 * Fornisce un endpoint per ottenere le informazioni sui giochi dell'utente autenticato.
 */
@RestController
@RequestMapping(path = "/api/game/info")
public class GameInfoController {
    private final IPuzzleService puzzleService;

    /**
     * Costruisce un'istanza di GameInfoController con il servizio di puzzle specificato.
     *
     * @param puzzleService il servizio di puzzle utilizzato per gestire le operazioni di gioco
     */
    public GameInfoController(IPuzzleService puzzleService) {
        this.puzzleService = puzzleService;
    }

    /**
     * Restituisce una lista di GameInfoResponse contenente le informazioni sui giochi dell'utente autenticato.
     *
     * @param page il numero di pagina (predefinito: 0)
     * @param size la dimensione della pagina (predefinito: 10)
     * @return una ResponseEntity contenente la lista di GameInfoResponse con le informazioni sui giochi
     */
    @GetMapping
    public ResponseEntity<List<GameInfoResponse>> getGames(@RequestParam(defaultValue = "0") int page,
                                                           @RequestParam(defaultValue = "10") int size) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return ResponseEntity.ok(puzzleService.findGamesByUser(authentication.getName(), PageRequest.of(page, size))
                .stream()
                .map(GameInfoResponse::from)
                .collect(Collectors.toList()));
    }

    /**
     *
     * @param gameId l'id della partita di cui si vogliono ottenere le informazioni
     * @return una ResponseEntity contenente le informazioni riguardanti la partita
     */
    @GetMapping("/{gameId}")
    public ResponseEntity<GameInfoResponse> getGames(@PathVariable(name = "gameId") long gameId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        GameView gameView = puzzleService.findGameViewById(gameId).orElseThrow(GameNotFoundException::new);
        if(!gameView.getPlayer().getEmail().equals(authentication.getName())) {
            throw new GameNotFoundException();
        }
        return ResponseEntity.ok(GameInfoResponse.from(gameView));
    }
}
