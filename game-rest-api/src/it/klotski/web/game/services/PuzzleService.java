package it.klotski.web.game.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.klotski.web.game.config.StartConfiguration;
import it.klotski.web.game.config.StartConfigurations;
import it.klotski.web.game.domain.Direction;
import it.klotski.web.game.domain.Game;
import it.klotski.web.game.domain.Move;
import it.klotski.web.game.domain.User;
import it.klotski.web.game.exceptions.UserNotFoundException;
import it.klotski.web.game.payload.reponses.BoardResponse;
import it.klotski.web.game.payload.reponses.TileResponse;
import it.klotski.web.game.payload.requests.BoardRequest;
import it.klotski.web.game.payload.requests.MoveRequest;
import it.klotski.web.game.repositories.IGameRepository;
import it.klotski.web.game.repositories.IMoveRepository;
import it.klotski.web.game.repositories.IUserRepository;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Service
public class PuzzleService implements IPuzzleService {
    private static final String PATH = "classpath:config/startConfigurations.json";
    private final StartConfigurations startConfigurations;
    private final IGameRepository gameRepository;
    private final IMoveRepository moveRepository;
    private final IUserRepository userRepository;

    public PuzzleService(ResourceLoader resourceLoader, IGameRepository gameRepository,
                         IMoveRepository moveRepository, IUserRepository userRepository) throws IOException {
        this.gameRepository = gameRepository;
        this.moveRepository = moveRepository;
        this.userRepository = userRepository;
        ObjectMapper objectMapper = new ObjectMapper();
        File file = resourceLoader.getResource(PATH).getFile();
        this.startConfigurations = objectMapper.readValue(file, StartConfigurations.class);
    }


    @Override
    public List<Game> findGamesByUser(String email, Pageable pageable) {
        return gameRepository.findAllByPlayer_Email(email, pageable);
    }

    @Override
    public Optional<Game> findGameById(long id) {
        return gameRepository.findGameById(id);
    }

    @Override
    public List<Move> findMovesByGame(Game game, Pageable pageable) {
        return moveRepository.findAllByGame(game, pageable);
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
    public void createMove(MoveRequest moveRequest, BoardRequest boardRequest, Game game) throws JsonProcessingException {
        Direction direction = moveRequest.getDirection();

        Move move = new Move();
        move.setTileId(moveRequest.getTile().id());
        move.setGame(game);
        move.setDirection(moveRequest.getDirection());
        move.setBoardHash(boardRequest.getBoardHash());
        move.setDirection(direction);

        moveRepository.save(move);
    }

    @Override
    public BoardResponse createGameFromRandomConfiguration(String email) {
        int configuration = ThreadLocalRandom.current().nextInt(0,startConfigurations.configurations().size());
        return createGameFromConfiguration(email, configuration);
    }

    @Override
    public BoardResponse createGameFromConfiguration(String email, int configurationId) {
        StartConfiguration randomConfiguration = startConfigurations.configurations().get(configurationId);
        User user = userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);

        Game game = new Game();
        game.setPlayer(user);
        game.setStartConfigurationId(randomConfiguration.id());

        gameRepository.save(game);

        return new BoardResponse(randomConfiguration.tiles()
                .stream()
                .map(TileResponse::from)
                .collect(Collectors.toList()));
    }

}
