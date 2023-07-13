package it.klotski.web.game.controller;

import com.google.gson.Gson;
import it.klotski.web.game.configs.Board;
import it.klotski.web.game.controllers.MoveController;
import it.klotski.web.game.domain.game.Game;
import it.klotski.web.game.domain.move.Direction;
import it.klotski.web.game.domain.tile.ITile;
import it.klotski.web.game.domain.tile.strategy.RectangularTileMatrixInsertionStrategy;
import it.klotski.web.game.domain.tile.visitor.ITileVisitor;
import it.klotski.web.game.domain.tile.visitor.RectangularTileVisitor;
import it.klotski.web.game.domain.user.User;
import it.klotski.web.game.payload.requests.MoveRequest;
import it.klotski.web.game.repositories.IGameRepository;
import it.klotski.web.game.repositories.IUserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.TreeSet;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class MoveControllerTest {
    private MockMvc mvc;
    @Autowired
    private MoveController moveController;
    @Autowired
    private Gson gson;
    @MockBean
    private IUserRepository userRepository;
    @MockBean
    private IGameRepository gameRepository;
    @Autowired
    private List<Board> boardConfiguration;

    private static final int EXISTSENT_GAME_ID = 1;
    private static final int EXISTENT_USER_ID = 1;
    private static final int EXISTENT_CONFIGURATION_ID = 0;
    private static final User USER = User.builder()
            .id(EXISTENT_USER_ID)
            .email("example@gmail.com")
            .password("password")
            .build();
    private static final Game NEWLY_CREATED_GAME = Game.builder()
            .player(USER)
            .startConfigurationId(EXISTENT_CONFIGURATION_ID)
            .build();
    private static final Game GAME = Game.builder()
            .id(EXISTSENT_GAME_ID)
            .player(USER)
            .startConfigurationId(EXISTENT_CONFIGURATION_ID)
            .createdAt(Timestamp.from(Instant.now()))
            .finished(false)
            .build();

    @BeforeEach
    public void setup() {
        User user = new User();
        user.setId(1);
        user.setEmail("example@gmail.com");
        user.setPassword("password");
        Mockito.when(userRepository.findByEmail("example@gmail.com")).thenReturn(Optional.of(user));
        //Init MockMvc Object and build
        mvc = MockMvcBuilders.standaloneSetup(moveController)
                .setMessageConverters(new GsonHttpMessageConverter(gson))
                .build();
        Mockito.when(gameRepository.findGameById(1)).thenReturn(Optional.of(GAME));
    }

    @Test
    @WithMockUser(username = "example@gmail.com")
    public void givenUnfinishedGame_whenIsPossibleToMoveTile_thenOkIsReturned() throws Exception {

        Board startConfig = boardConfiguration.get(0);
        TreeSet<ITile> tileConfigs = startConfig.getTiles();
        ITile[][] tiles = new ITile[startConfig.getBoardHeight()][startConfig.getBoardWidth()];
        ITileVisitor tileVisitor = new RectangularTileVisitor(new RectangularTileMatrixInsertionStrategy(tiles,
                startConfig.getBoardHeight(),
                startConfig.getBoardWidth()));
        for (ITile tile : tileConfigs) {
            tile.accept(tileVisitor);
        }

        ITile tileToMove = startConfig.getTiles().stream().filter(tile -> tile.getId() == 10).findFirst().orElseThrow();

        MoveRequest moveRequest = new MoveRequest(tileToMove,
                Direction.LEFT,
                1,
                startConfig.getTiles());
        String jsonContent = gson.toJson(moveRequest);
        MvcResult result = mvc.perform(post("/api/moves").content(jsonContent)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andReturn();
        Assertions.assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());

    }


    @Test
    @WithMockUser(username = "example@gmail.com")
    public void givenUnfinishedGame_whenIsImpossibleToMoveTile_thenOkIsReturned() throws Exception {

        Board startConfig = boardConfiguration.get(0);
        TreeSet<ITile> tileConfigs = startConfig.getTiles();
        ITile[][] tiles = new ITile[startConfig.getBoardHeight()][startConfig.getBoardWidth()];
        ITileVisitor tileVisitor = new RectangularTileVisitor(new RectangularTileMatrixInsertionStrategy(tiles,
                startConfig.getBoardHeight(),
                startConfig.getBoardWidth()));
        for (ITile tile : tileConfigs) {
            tile.accept(tileVisitor);
        }

        ITile tileToMove = startConfig.getTiles().stream().filter(tile -> tile.getId() == 10).findFirst().orElseThrow();

        MoveRequest moveRequest = new MoveRequest(tileToMove,
                Direction.LEFT,
                1,
                startConfig.getTiles());
        String jsonContent = gson.toJson(moveRequest);
        MvcResult result = mvc.perform(post("/api/moves").content(jsonContent)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andReturn();
        Assertions.assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());

    }
}
