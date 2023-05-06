package it.klotski.web.game.controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import it.klotski.web.game.domain.game.Game;
import it.klotski.web.game.domain.user.User;
import it.klotski.web.game.payload.reponses.GameResponse;
import it.klotski.web.game.repositories.IGameRepository;
import it.klotski.web.game.repositories.IUserRepository;
import it.klotski.web.game.services.user.PuzzleService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

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
    private PuzzleService puzzleService;
    @MockBean
    private IUserRepository userRepository;
    @MockBean
    private IGameRepository gameRepository;

    @BeforeEach
    public void setup() {
        User user = new User();
        user.setId(1);
        user.setEmail("example@gmail.com");
        user.setPassword("password");
        Mockito.when(userRepository.findByEmail("example@gmail.com")).thenReturn(Optional.of(user));
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
    public void whenConfigurationIsRandom_thenOkIsReceived() throws Exception {
        MvcResult result = mvc.perform(post("/api/games").with(csrf())).andReturn();
        Assertions.assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
    }

    @Test
    @WithMockUser(username = "example@gmail.com")
    public void givenRandomConfiguration_whenCreatingNewGame_thenOkIsReceived() throws Exception {
        MvcResult result = mvc.perform(post("/api/games").with(csrf()).requestAttr("startConfigId", 0)).andReturn();
        Assertions.assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
    }

    @Test
    @WithMockUser(username = "example@gmail.com")
    public void givenNonexistentConfigurationId_whenCreatingNewGame_thenBadRequestReceived() throws Exception {
        MvcResult result = mvc.perform(post("/api/games").with(csrf()).param("startConfigId", "1")).andReturn();
        Assertions.assertEquals(HttpStatus.BAD_REQUEST.value(), result.getResponse().getStatus());
    }

    @Test
    @WithMockUser(username = "example@gmail.com")
    public void givenNoGameInstances_whenGettingGamesFromUser_thenOkWithEmptyListIsReturned() throws Exception {
        Mockito.when(puzzleService.findGamesByUser("example@gmail.com", PageRequest.of(0, 10))).thenReturn(List.of());
        MvcResult result = mvc.perform(get("/api/games")).andReturn();
        Assertions.assertEquals(List.of(), new Gson().fromJson(
                result.getResponse().getContentAsString(),
                new TypeToken<List<Game>>() {}.getType())
        );
    }

    @Test
    @WithMockUser(username = "example@gmail.com")
    public void givenGameInstances_whenGettingGamesFromUser_thenOkWithFilledListIsReturned() throws Exception {
        List<Game> games = List.of(Game.builder()
                        .player(null).date(1000).id(1).isFinished(true).duration(10000).startConfigurationId(0)
                        .build(),
                Game.builder()
                        .player(null).date(2000).id(2).isFinished(false).duration(20000).startConfigurationId(0)
                        .build(),
                Game.builder()
                        .player(null).date(3000).id(3).isFinished(true).duration(30000).startConfigurationId(0)
                        .build(),
                Game.builder()
                        .player(null).date(4000).id(4).isFinished(false).duration(40000).startConfigurationId(0)
                        .build(),
                Game.builder()
                        .player(null).date(5000).id(5).isFinished(true).duration(50000).startConfigurationId(0)
                        .build(),
                Game.builder()
                        .player(null).date(6000).id(6).isFinished(false).duration(60000).startConfigurationId(0)
                        .build());
        List<GameResponse> response = games.stream().map(GameResponse::from).toList();

        Mockito.when(gameRepository.findAllByPlayer_Email("example@gmail.com", PageRequest.of(0, 10)))
                .thenReturn(games);


        MvcResult result = mvc.perform(get("/api/games")).andReturn();
        Assertions.assertEquals(response, new Gson().fromJson(
                result.getResponse().getContentAsString(),
                new TypeToken<List<GameResponse>>() {}.getType())
        );
    }
}
