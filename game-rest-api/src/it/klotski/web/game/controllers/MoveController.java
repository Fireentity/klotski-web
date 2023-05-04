package it.klotski.web.game.controllers;

import it.klotski.web.game.domain.game.Game;
import it.klotski.web.game.domain.move.Move;
import it.klotski.web.game.domain.user.User;
import it.klotski.web.game.exceptions.GameNotFoundException;
import it.klotski.web.game.exceptions.InvalidBoardConfigurationException;
import it.klotski.web.game.exceptions.UserNotFoundException;
import it.klotski.web.game.payload.requests.MoveRequest;
import it.klotski.web.game.repositories.IUserRepository;
import it.klotski.web.game.services.user.IPuzzleService;
import it.klotski.web.game.services.user.strategy.IRectangularTileStrategy;
import it.klotski.web.game.services.user.strategy.RectangularTileMoveValidationStrategy;
import it.klotski.web.game.services.user.visitor.ITileVisitor;
import it.klotski.web.game.services.user.visitor.RectangularTileVisitor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/api/moves")
public class MoveController {
    private final IUserRepository userRepository;
    private final IPuzzleService puzzleService;

    @Autowired
    public MoveController(IUserRepository userRepository, IPuzzleService puzzleService) {
        this.userRepository = userRepository;
        this.puzzleService = puzzleService;
    }

    @PostMapping
    public ResponseEntity<Void> moveTile(@RequestBody MoveRequest moveRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByEmail(authentication.getName()).orElseThrow(UserNotFoundException::new);
        Game game = puzzleService.findGameById(moveRequest.getGameId()).orElseThrow(GameNotFoundException::new);
        if (game.getPlayer().getId() != user.getId()) {
            throw new GameNotFoundException();
        }
        IRectangularTileStrategy validationStrategy = new RectangularTileMoveValidationStrategy(
                moveRequest.getBoardRequest().getTiles(),
                moveRequest.getDirection());
        List<ITileVisitor> visitors = List.of(new RectangularTileVisitor(validationStrategy));
        visitors.forEach(visitor -> moveRequest.getTile().accept(visitor));

        Optional<Move> lastMove = puzzleService.findLastMove(game);
        if (lastMove.isEmpty()) {
            puzzleService.createMove(moveRequest, game);
            return ResponseEntity.ok().build();
        }

        if(!lastMove.get().getBoardHash().equals(moveRequest.getBoardRequest().getBoardHash())) {
            throw new InvalidBoardConfigurationException();
        }

        puzzleService.createMove(moveRequest, game);
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
