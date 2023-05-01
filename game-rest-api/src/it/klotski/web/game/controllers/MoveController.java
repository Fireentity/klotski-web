package it.klotski.web.game.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import it.klotski.web.game.domain.Game;
import it.klotski.web.game.domain.Move;
import it.klotski.web.game.domain.User;
import it.klotski.web.game.payload.requests.BoardRequest;
import it.klotski.web.game.payload.requests.MoveRequest;
import it.klotski.web.game.exceptions.GameNotFoundException;
import it.klotski.web.game.exceptions.InvalidBoardConfigurationException;
import it.klotski.web.game.services.IPuzzleService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/api/moves")
@RequiredArgsConstructor
public class MoveController {
    private final IPuzzleService puzzleService;

    @PostMapping
    public ResponseEntity<Void> moveTile(@RequestBody MoveRequest moveRequest, @RequestBody BoardRequest boardRequest) throws JsonProcessingException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        Game game = puzzleService.findGameById(moveRequest.getGameId()).orElseThrow(GameNotFoundException::new);
        if (game.getPlayer().getId() != user.getId()) {
            throw new GameNotFoundException();
        }
        Optional<Move> lastMove = puzzleService.findLastMove(game);
        if (lastMove.isEmpty()) {
            puzzleService.createMove(moveRequest, boardRequest, game);
            return ResponseEntity.ok().build();
        }

        if(!lastMove.get().getBoardHash().equals(boardRequest.getBoardHash())) {
            throw new InvalidBoardConfigurationException();
        }

        puzzleService.createMove(moveRequest, boardRequest, game);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<Move>> getMoves(@RequestBody long gameId,
                                               @RequestParam(defaultValue = "0") int page,
                                               @RequestParam(defaultValue = "10") int size) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        Optional<Game> game = puzzleService.findGameById(gameId);
        if (game.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        if (game.get().getPlayer().getId() != user.getId()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(puzzleService.findMovesByGame(game.get(), PageRequest.of(page, size)));
    }

    @DeleteMapping
    public ResponseEntity<Void> reset(@RequestBody long gameId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        Optional<Game> game = puzzleService.findGameById(gameId);
        if (game.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        if (user.getId() != game.get().getPlayer().getId()) {
            return ResponseEntity.notFound().build();
        }

        if (game.get().isFinished()) {
            return ResponseEntity.notFound().build();
        }

        puzzleService.deleteMovesByGame(game.get());
        return ResponseEntity.ok().build();
    }
}
