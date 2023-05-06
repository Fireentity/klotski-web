package it.klotski.web.game.services.user;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import it.klotski.web.game.configs.StartConfiguration;
import it.klotski.web.game.configs.StartConfigurations;
import it.klotski.web.game.domain.game.Game;
import it.klotski.web.game.domain.move.Direction;
import it.klotski.web.game.domain.move.Move;
import it.klotski.web.game.domain.tile.ITile;
import it.klotski.web.game.domain.user.User;
import it.klotski.web.game.exceptions.ConfigurationNotFoundException;
import it.klotski.web.game.payload.reponses.BoardResponse;
import it.klotski.web.game.payload.requests.MoveRequest;
import it.klotski.web.game.repositories.IGameRepository;
import it.klotski.web.game.repositories.IMoveRepository;
import it.klotski.web.game.services.user.strategy.RectangularTileMatrixInsertionStrategy;
import it.klotski.web.game.services.user.visitor.ITileVisitor;
import it.klotski.web.game.services.user.visitor.RectangularTileVisitor;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

import static it.klotski.web.game.constants.ApplicationConstants.ADAPTER_FACTORY;

@Service
public class PuzzleService implements IPuzzleService {
    private static final String PATH = "classpath:config/startConfigurations.json";
    private final StartConfigurations startConfigurations;
    private final IGameRepository gameRepository;
    private final IMoveRepository moveRepository;
    private final UserDetailsService userService;

    public PuzzleService(ResourceLoader resourceLoader, IGameRepository gameRepository,
                         IMoveRepository moveRepository, UserDetailsService userService) throws IOException {
        this.gameRepository = gameRepository;
        this.moveRepository = moveRepository;
        this.userService = userService;
        Gson gson = new GsonBuilder().registerTypeAdapterFactory(ADAPTER_FACTORY).create();
        File file = resourceLoader.getResource(PATH).getFile();
        this.startConfigurations = gson.fromJson(Files.readString(file.toPath()), StartConfigurations.class);
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
    public void createMove(MoveRequest moveRequest, Game game) {
        Direction direction = moveRequest.getDirection();

        Move move = new Move();
        move.setTileId(moveRequest.getTile().getId());
        move.setGame(game);
        move.setDirection(moveRequest.getDirection());
        move.setBoardHash(moveRequest.getBoardRequest().getBoardHash());
        move.setDirection(direction);

        moveRepository.save(move);
    }

    @Override
    public BoardResponse createGameFromRandomConfiguration(String email) {
        int configuration = ThreadLocalRandom.current().nextInt(0,startConfigurations.getConfigurations().size());
        return createGameFromConfiguration(email, configuration);
    }

    @Override
    public BoardResponse createGameFromConfiguration(String email, int configurationId) {
        if(configurationId >= startConfigurations.getConfigurations().size()) {
            throw new ConfigurationNotFoundException();
        }
        StartConfiguration randomConfiguration = startConfigurations.getConfigurations().get(configurationId);
        User user = (User)userService.loadUserByUsername(email);
        Game game = new Game();
        game.setPlayer(user);
        game.setStartConfigurationId(randomConfiguration.getId());

        gameRepository.save(game);

        ITile[][] tiles = new ITile[randomConfiguration.getBoardHeight()][randomConfiguration.getBoardWidth()];
        ITileVisitor tileVisitor = new RectangularTileVisitor(new RectangularTileMatrixInsertionStrategy(tiles,
                randomConfiguration.getBoardHeight(),
                randomConfiguration.getBoardWidth()));
        for(ITile tile : randomConfiguration.getTiles()) {
            tile.accept(tileVisitor);
        }
        return new BoardResponse(tiles);
    }

}
