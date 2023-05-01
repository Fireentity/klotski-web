package it.klotski.web.game.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import it.klotski.web.game.domain.Game;
import it.klotski.web.game.domain.Move;
import it.klotski.web.game.payload.reponses.BoardResponse;
import it.klotski.web.game.payload.requests.BoardRequest;
import it.klotski.web.game.payload.requests.MoveRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface IPuzzleService {

    List<Game> findGamesByUser(String email, Pageable pageable);

    Optional<Game> findGameById(long id);

    List<Move> findMovesByGame(Game game, Pageable pageable);

    void deleteMovesByGame(Game game);

    Optional<Move> findLastMove(Game game);

    void createMove(MoveRequest moveRequest, BoardRequest boardRequest, Game game) throws JsonProcessingException;

    BoardResponse createGameFromRandomConfiguration(String email);

    BoardResponse createGameFromConfiguration(String email, int configurationId);
}
