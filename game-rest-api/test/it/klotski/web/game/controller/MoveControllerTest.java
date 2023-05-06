package it.klotski.web.game.controller;

import com.google.gson.Gson;
import it.klotski.web.game.configs.StartConfiguration;
import it.klotski.web.game.configs.StartConfigurations;
import it.klotski.web.game.constants.ApplicationConstants;
import it.klotski.web.game.controllers.MoveController;
import it.klotski.web.game.domain.game.Game;
import it.klotski.web.game.domain.move.Direction;
import it.klotski.web.game.domain.tile.ITile;
import it.klotski.web.game.domain.user.User;
import it.klotski.web.game.payload.requests.BoardRequest;
import it.klotski.web.game.payload.requests.MoveRequest;
import it.klotski.web.game.repositories.IGameRepository;
import it.klotski.web.game.repositories.IMoveRepository;
import it.klotski.web.game.repositories.IUserRepository;
import it.klotski.web.game.services.user.PuzzleService;
import it.klotski.web.game.services.user.strategy.RectangularTileMatrixInsertionStrategy;
import it.klotski.web.game.services.user.visitor.ITileVisitor;
import it.klotski.web.game.services.user.visitor.RectangularTileVisitor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class MoveControllerTest {
    private MockMvc mvc;
    @Autowired
    private MoveController moveController;
    @Autowired
    private Gson gson;
    @Autowired
    private WebApplicationContext webApplicationContext;
    @MockBean
    private IUserRepository userRepository;
    @MockBean
    private IGameRepository gameRepository;
    @MockBean
    private IMoveRepository moveRepository;
    @Autowired
    private PuzzleService puzzleService;
    @Autowired
    private ResourceLoader resourceLoader;

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
    }

    @Test
    @WithMockUser(username = "example@gmail.com")
    public void givenUnfinishedGame_whenIsPossibleToMoveTile_thenOkIsReturned() throws Exception {
        User user = new User();
        user.setId(1);
        user.setEmail("example@gmail.com");
        user.setPassword("password");
        Mockito.when(gameRepository.findGameById(1)).thenReturn(Optional.of(new Game(1, user,0,1000,1000,false)));

        File configFile = resourceLoader.getResource(ApplicationConstants.START_CONFIGURATIONS_FILE_PATH).getFile();
        String json = Files.readString(configFile.toPath(), StandardCharsets.UTF_8);
        StartConfiguration startConfig = gson.fromJson(json, StartConfigurations.class).getConfigurations().get(0);
        List<ITile> tileConfigs = startConfig.getTiles();
        ITile[][] tiles = new ITile[startConfig.getBoardHeight()][startConfig.getBoardWidth()];
        ITileVisitor tileVisitor = new RectangularTileVisitor(new RectangularTileMatrixInsertionStrategy(tiles,
                startConfig.getBoardHeight(),
                startConfig.getBoardWidth()));
        for (ITile tile : tileConfigs) {
            tile.accept(tileVisitor);
        }

        ITile tileToMove = startConfig.getTiles().get(10);

        MoveRequest moveRequest = new MoveRequest(tileToMove,
                Direction.LEFT,
                1,
                new BoardRequest(tiles));
        String jsonContent = gson.toJson(moveRequest);
        MvcResult result = mvc.perform(post("/api/moves").content(jsonContent)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andReturn();
        Assertions.assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());

    }
}
