package it.klotski.web.game.services.user;

import it.klotski.web.game.configs.Board;
import it.klotski.web.game.domain.move.Move;
import it.klotski.web.game.domain.game.Game;

import it.klotski.web.game.payload.reponses.GameResponse;
import it.klotski.web.game.payload.requests.MoveRequest;
import it.klotski.web.game.tile.ITile;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface IPuzzleService {

    List<Game> findGamesByUser(String email, Pageable pageable);

    Optional<Game> findLastUnfinishedGame(String email);

    Optional<Game> findGameById(long id);

    List<Move> findMovesByGame(Game game, Pageable pageable);

    void deleteMovesByGame(Game game);

    Optional<Move> findLastMove(Game game);

    void createMove(MoveRequest moveRequest, Game game, ITile[][] board, String boardHash);

    GameResponse createGameFromConfiguration(String email, int configurationId);

    Board calculateCurrentConfiguration(Game game);
}
