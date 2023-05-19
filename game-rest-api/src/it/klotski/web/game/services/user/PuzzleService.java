package it.klotski.web.game.services.user;

import it.klotski.web.game.configs.Board;
import it.klotski.web.game.domain.move.Move;
import it.klotski.web.game.domain.game.Game;
import it.klotski.web.game.move.Direction;
import it.klotski.web.game.tile.ITile;
import it.klotski.web.game.domain.user.User;
import it.klotski.web.game.exceptions.ConfigurationNotFoundException;
import it.klotski.web.game.payload.reponses.GameResponse;
import it.klotski.web.game.payload.requests.MoveRequest;
import it.klotski.web.game.repositories.IGameRepository;
import it.klotski.web.game.repositories.IMoveRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.*;

import static it.klotski.web.game.constants.ApplicationConstants.DEFAULT_PAGE_SIZE;

@Service
public class PuzzleService implements IPuzzleService {
    private final List<Board> boards;
    private final IGameRepository gameRepository;
    private final IMoveRepository moveRepository;
    private final UserDetailsService userService;

    public PuzzleService(List<Board> boards,
                         IGameRepository gameRepository,
                         IMoveRepository moveRepository,
                         UserDetailsService userService) {
        this.boards = boards;
        this.gameRepository = gameRepository;
        this.moveRepository = moveRepository;
        this.userService = userService;
    }


    @Override
    public List<Game> findGamesByUser(String email, Pageable pageable) {
        return gameRepository.findAllByPlayer_Email(email, pageable);
    }

    @Override
    public Optional<Game> findLastUnfinishedGame(String email) {
        return gameRepository.findFirstByFinishedAndPlayer_EmailOrderByCreatedAtDesc(false, email);
    }

    @Override
    public Optional<Game> findGameById(long id) {
        return gameRepository.findGameById(id);
    }

    @Override
    public List<Move> findMovesByGame(Game game, Pageable pageable) {
        return moveRepository.findAllByGameOrderByCreatedAtAsc(game, pageable);
    }

    @Override
    public void deleteMovesByGame(Game game) {
        moveRepository.deleteAllByGame(game);
    }

    @Override
    public Optional<Move> findLastMove(Game game) {
        return moveRepository.findFirstByGameOrderByCreatedAtDesc(game);
    }

    @Override
    public void createMove(MoveRequest moveRequest, Game game, ITile[][] board, String boardHash) {
        Direction direction = moveRequest.getDirection();

        Move move = new Move();
        move.setTileId(moveRequest.getTileToMove().getId());
        move.setGame(game);
        move.setDirection(moveRequest.getDirection());
        move.setBoardHash(boardHash);
        move.setDirection(direction);

        moveRepository.save(move);
    }

    @Override
    public GameResponse createGameFromConfiguration(String email, int configurationId) {
        if (configurationId >= boards.size()) {
            throw new ConfigurationNotFoundException();
        }
        User user = (User) userService.loadUserByUsername(email);
        Game game = new Game();
        game.setPlayer(user);
        game.setStartConfigurationId(configurationId);

        Board board = boards.get(configurationId);
        return GameResponse.from(gameRepository.save(game), board);
    }

    @Override
    public Board calculateCurrentConfiguration(Game game) {
        Pageable pageable = PageRequest.of(0, DEFAULT_PAGE_SIZE);
        List<Move> moves = this.findMovesByGame(game, PageRequest.of(0, DEFAULT_PAGE_SIZE));
        Board board = boards.get(game.getStartConfigurationId());
        Map<Integer, ITile> tiles = new HashMap<>();
        board.getTiles().forEach(tile -> tiles.put(tile.getId(), tile));
        while(!moves.isEmpty()) {
            moves.forEach(move -> tiles.put(move.getTileId(), tiles.get(move.getTileId()).move(move.getDirection())));
            pageable = pageable.next();
            moves = this.findMovesByGame(game, pageable);
        }
        return board.withTiles(new TreeSet<>(tiles.values()));
    }

}
