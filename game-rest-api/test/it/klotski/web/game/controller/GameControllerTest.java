package it.klotski.web.game.controller;

import com.google.gson.Gson;
import it.klotski.web.game.configs.Board;
import it.klotski.web.game.controllers.GameController;
import it.klotski.web.game.domain.game.Game;
import it.klotski.web.game.domain.game.GameView;
import it.klotski.web.game.domain.user.User;
import it.klotski.web.game.exceptions.handler.GlobalExceptionHandler;
import it.klotski.web.game.payload.reponses.GameResponse;
import it.klotski.web.game.payload.requests.GameRequest;
import it.klotski.web.game.repositories.IGameRepository;
import it.klotski.web.game.repositories.IGameViewRepository;
import it.klotski.web.game.repositories.IUserRepository;
import it.klotski.web.game.services.PuzzleService;
import it.klotski.web.game.services.UserService;
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
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class GameControllerTest {
    private MockMvc mvc;
    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    private List<Board> boards;
    @Autowired
    private PuzzleService puzzleService;
    @MockBean
    private UserService userService;
    @MockBean
    private IUserRepository userRepository;
    @MockBean
    private IGameRepository gameRepository;
    @MockBean
    private IGameViewRepository gameViewRepository;
    @Autowired
    private GameController gameController;
    @Autowired
    private Gson gson;
    @Autowired
    private GlobalExceptionHandler globalExceptionHandler;

    private static final User USER = User.builder().id(1).email("example@gmail.com").password("password").build();
    private static final Game GAME = Game.builder().id(1).player(USER).startConfigurationId(0).createdAt(Timestamp.from(Instant.now())).build();


    @BeforeEach
    public void setup() {

        Mockito.when(userRepository.findByEmail("example@gmail.com")).thenReturn(Optional.of(USER));
        Mockito.when(userService.loadUserByUsername("example@gmail.com")).thenReturn(USER);
        Mockito.when(gameRepository.save(GAME)).thenReturn(GAME);
        Mockito.when(gameViewRepository.findGameViewById(1L)).thenReturn(Optional.of(GameView.from(GAME,0)));
        //Init MockMvc Object and build
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(SecurityMockMvcConfigurers.springSecurity())
                .build();
    }

    @Test
    public void whenUserIsNotLoggedIn_thenForbiddenRequestIsReceived() throws Exception {
        MvcResult result = mvc.perform(post("/api/games")).andReturn();
        Assertions.assertEquals(HttpStatus.FORBIDDEN.value(), result.getResponse().getStatus());
    }

    @Test
    @WithMockUser(username = "example@gmail.com")
    public void givenExistentConfiguration_whenCreatingNewGame_thenOkIsReceived() throws Exception {
        MvcResult result = mvc.perform(post("/api/games")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new Gson().toJson(new GameRequest(0)))
                .with(csrf())).andReturn();
        Assertions.assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
    }

    @Test
    @WithMockUser(username = "example@gmail.com")
    public void givenNonexistentConfigurationId_whenCreatingNewGame_thenBadRequestReceived() throws Exception {
        MvcResult result = mvc.perform(post("/api/games")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new Gson().toJson(new GameRequest(5)))
                .with(csrf())).andReturn();
        Assertions.assertEquals(HttpStatus.BAD_REQUEST.value(), result.getResponse().getStatus());
    }

    @Test
    @WithMockUser(username = "example@gmail.com")
    public void givenGameId_whenGameNotExists_thenGameViewIsReturned() throws Exception {
        MvcResult result = mvc.perform(get("/api/games?gameId=" + 2L)).andReturn();
        Assertions.assertEquals(HttpStatus.BAD_REQUEST.value(), result.getResponse().getStatus());
    }

    @Test
    @WithMockUser(username = "example@gmail.com")
    public void givenGameId_whenGameExists_thenGameViewIsReturned() throws Exception {
        MvcResult result = mvc.perform(get("/api/games?gameId=" + 1L)).andReturn();
        GameResponse gameViewTest = GameResponse.from(GameView.from(GAME,0),boards.get(0));
        GameResponse gameViewResult = gson.fromJson(
                result.getResponse().getContentAsString(),
                GameResponse.class);
        Assertions.assertEquals(gameViewTest, gameViewResult);
    }
}
