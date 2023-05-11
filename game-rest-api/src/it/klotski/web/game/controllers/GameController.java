package it.klotski.web.game.controllers;

import it.klotski.web.game.configs.Board;
import it.klotski.web.game.domain.game.Game;
import it.klotski.web.game.domain.user.User;
import it.klotski.web.game.exceptions.GameNotFoundException;
import it.klotski.web.game.payload.reponses.GameResponse;
import it.klotski.web.game.payload.requests.GameRequest;
import it.klotski.web.game.services.user.IPuzzleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(path = "/api/games")
public class GameController {
    private final IPuzzleService puzzleService;

    @Autowired
    public GameController(IPuzzleService puzzleService) {
        this.puzzleService = puzzleService;
    }

    @PostMapping
    public ResponseEntity<GameResponse> createGame(@RequestBody GameRequest gameRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return ResponseEntity.ok(puzzleService.createGameFromConfiguration(authentication.getName(), gameRequest.getStartConfigId()));
    }

    @GetMapping
    public ResponseEntity<GameResponse> getGame(@RequestParam long gameId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        Game game = puzzleService.findGameById(gameId).orElseThrow(GameNotFoundException::new);
        if(game.getPlayer().getId() != user.getId()) {
            throw new GameNotFoundException();
        }

        Board board = puzzleService.calculateCurrentConfiguration(game);
        return ResponseEntity.ok(GameResponse.from(game, board));
    }

    @GetMapping(path = "/unfinished")
    public ResponseEntity<GameResponse> getLastUnfinishedGame() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Optional<Game> gameOptional = puzzleService.findLastUnfinishedGame(authentication.getName());
        if (gameOptional.isEmpty()) {
            return ResponseEntity.ok().build();
        }

        Game game = gameOptional.get();
        Board board = puzzleService.calculateCurrentConfiguration(game);

        return ResponseEntity.ok(GameResponse.from(game, board));
    }
}
