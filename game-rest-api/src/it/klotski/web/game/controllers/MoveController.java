package it.klotski.web.game.controllers;

import com.google.gson.Gson;
import it.klotski.web.game.configs.Board;
import it.klotski.web.game.domain.game.Game;
import it.klotski.web.game.domain.move.Move;
import it.klotski.web.game.domain.tile.ITile;
import it.klotski.web.game.domain.user.User;
import it.klotski.web.game.exceptions.GameNotFoundException;
import it.klotski.web.game.exceptions.InvalidBoardConfigurationException;
import it.klotski.web.game.exceptions.UserNotFoundException;
import it.klotski.web.game.payload.reponses.MoveResponse;
import it.klotski.web.game.payload.requests.MoveRequest;
import it.klotski.web.game.repositories.IUserRepository;
import it.klotski.web.game.services.user.IPuzzleService;
import it.klotski.web.game.services.user.strategy.RectangularTileMatrixInsertionStrategy;
import it.klotski.web.game.services.user.strategy.RectangularTileMoveValidationStrategy;
import it.klotski.web.game.services.user.visitor.ITileVisitor;
import it.klotski.web.game.services.user.visitor.RectangularTileVisitor;
import it.klotski.web.game.services.user.visitor.WinningTileVisitor;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;
import java.util.TreeSet;

@RestController
@RequestMapping(path = "/api/moves")
public class MoveController {
    private final Gson gson;
    private final IUserRepository userRepository;
    private final IPuzzleService puzzleService;
    private final List<Board> boards;

    @Autowired
    public MoveController(Gson gson,
                          IUserRepository userRepository,
                          IPuzzleService puzzleService,
                          List<Board> boards) {
        this.gson = gson;
        this.userRepository = userRepository;
        this.puzzleService = puzzleService;
        this.boards = boards;
    }

    @PostMapping
    public ResponseEntity<MoveResponse> moveTile(@RequestBody MoveRequest moveRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByEmail(authentication.getName()).orElseThrow(UserNotFoundException::new);
        Game game = puzzleService.findGameById(moveRequest.getGameId()).orElseThrow(GameNotFoundException::new);
        if (game.getPlayer().getId() != user.getId()) {
            throw new GameNotFoundException();
        }

        Board startConfiguration = boards.get(game.getStartConfigurationId());

        ITile[][] board = new ITile[startConfiguration.getBoardHeight()][startConfiguration.getBoardWidth()];
        RectangularTileMatrixInsertionStrategy insertionStrategy = new RectangularTileMatrixInsertionStrategy(board,
                startConfiguration.getBoardHeight(),
                startConfiguration.getBoardWidth());
        List<ITileVisitor> insertionVisitors = List.of(new RectangularTileVisitor(insertionStrategy), new WinningTileVisitor(insertionStrategy));
        moveRequest.getTiles().forEach(tile -> insertionVisitors.forEach(tile::accept));

        RectangularTileMoveValidationStrategy validationStrategy = new RectangularTileMoveValidationStrategy(
                board,
                moveRequest.getDirection());
        List<ITileVisitor> visitors = List.of(new RectangularTileVisitor(validationStrategy), new WinningTileVisitor(validationStrategy));
        visitors.forEach(visitor -> moveRequest.getTileToMove().accept(visitor));

        String boardHash = DigestUtils.md5DigestAsHex(gson.toJson(moveRequest.getTiles()).getBytes(StandardCharsets.UTF_8));

        if(!validationStrategy.isValid()) {
            return ResponseEntity.ok(new MoveResponse(moveRequest.getTiles()));
        }

        Optional<Move> lastMove = puzzleService.findLastMove(game);
        if (lastMove.isPresent() && !lastMove.get().getBoardHash().equals(boardHash)) {
            throw new InvalidBoardConfigurationException();
        }

        TreeSet<ITile> responseTiles = new TreeSet<>(moveRequest.getTiles());
        responseTiles.remove(moveRequest.getTileToMove());
        responseTiles.add(moveRequest.getTileToMove().move(moveRequest.getDirection()));
        String newBoardHash = DigestUtils.md5DigestAsHex(gson.toJson(responseTiles).getBytes(StandardCharsets.UTF_8));
        puzzleService.createMove(moveRequest, game, board, newBoardHash);
        return ResponseEntity.ok(new MoveResponse(responseTiles));
    }

    @GetMapping
    public ResponseEntity<List<Move>> getMoves(@RequestBody long gameId,
                                               @RequestParam(defaultValue = "0") int page,
                                               @RequestParam(defaultValue = "10") int size) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        Game game = puzzleService.findGameById(gameId).orElseThrow(GameNotFoundException::new);
        if (game.getPlayer().getId() != user.getId()) {
            throw new GameNotFoundException();
        }
        return ResponseEntity.ok(puzzleService.findMovesByGame(game, PageRequest.of(page, size)));
    }

    @Transactional
    @DeleteMapping
    public ResponseEntity<MoveResponse> reset(@RequestParam long gameId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        Game game = puzzleService.findGameById(gameId).orElseThrow(GameNotFoundException::new);

        if (user.getId() != game.getPlayer().getId()) {
            throw new GameNotFoundException();
        }

        if (game.isFinished()) {
            throw new GameNotFoundException();
        }

        Board board = boards.get(game.getStartConfigurationId());

        puzzleService.deleteMovesByGame(game);
        return ResponseEntity.ok(new MoveResponse(board.getTiles()));
    }
}
