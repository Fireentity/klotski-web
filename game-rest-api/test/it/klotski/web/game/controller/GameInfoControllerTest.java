package it.klotski.web.game.controller;

import com.google.gson.Gson;
import it.klotski.web.game.controllers.GameInfoController;
import it.klotski.web.game.domain.game.GameView;
import it.klotski.web.game.domain.user.User;
import it.klotski.web.game.payload.reponses.GameInfoResponse;
import it.klotski.web.game.repositories.IGameViewRepository;
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
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Optional;

import static it.klotski.web.game.constants.ApplicationConstants.DATE_FORMAT;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class GameInfoControllerTest {
    private MockMvc mvc;
    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    private GameInfoController gameInfoController;
    @Autowired
    private Gson gson;
    @MockBean
    private IGameViewRepository gameViewRepository;
    @MockBean
    private IUserRepository userRepository;
    private static final int GAME_ID = 1;
    private static final int GAME_CONFIGURATION_ID = 1;
    private static final Timestamp CURRENT_DATE = Timestamp.from(Instant.now());
    private static final String CURRENT_DATE_STRING = DATE_FORMAT.format(Timestamp.from(Instant.now()));
    private static final int EXISTENT_USER_ID = 1;
    private static final int MOVES = 5;
    private static final User USER = User.builder()
            .id(EXISTENT_USER_ID)
            .email("example@gmail.com")
            .password("password")
            .build();
    private static final GameInfoResponse GAME_INFO_RESPONSE = new GameInfoResponse(GAME_ID,
            GAME_CONFIGURATION_ID,
            CURRENT_DATE_STRING,
            MOVES,
            true);
    private static final GameView GAME_VIEW = GameView.builder()
            .createdAt(CURRENT_DATE)
            .updatedAt(CURRENT_DATE)
            .startConfigurationId(GAME_CONFIGURATION_ID)
            .finished(true)
            .moves(MOVES)
            .player(USER)
            .id(GAME_ID)
            .build();

    @BeforeEach
    public void setup() {
        Mockito.when(userRepository.findByEmail("example@gmail.com")).thenReturn(Optional.of(USER));
        Mockito.when(gameViewRepository.findGameViewById(GAME_ID)).thenReturn(Optional.of(GAME_VIEW));
        //Init MockMvc Object and build
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(SecurityMockMvcConfigurers.springSecurity())
                .build();
    }

    @Test
    @WithMockUser(username = "example@gmail.com")
    public void givenGameId_whenGettingGameInfo_thanGameInfoIsReturned() throws Exception {
        MvcResult result = mvc.perform(get("/api/game/info/" + GAME_ID).with(csrf())).andReturn();
        GameInfoResponse gameInfoResponse = gson.fromJson(result.getResponse().getContentAsString(), GameInfoResponse.class);
        Assertions.assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
        Assertions.assertEquals(gameInfoResponse, GAME_INFO_RESPONSE);
    }

    @Test
    @WithMockUser(username = "example@gmail.com")
    public void givenNonExistentGameId_whenGettingGameInfo_thanGameInfoIsReturned() throws Exception {
        MvcResult result = mvc.perform(get("/api/game/info/" + 10).with(csrf())).andReturn();
        Assertions.assertEquals(HttpStatus.BAD_REQUEST.value(), result.getResponse().getStatus());
    }
}
