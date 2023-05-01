package it.klotski.web.game.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.klotski.web.game.payload.requests.LoginRequest;
import it.klotski.web.game.payload.requests.RegisterRequest;
import it.klotski.web.game.repositories.IGameRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class GameControllerTest {
    private MockMvc mvc;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private IGameRepository gameRepository;
    @Autowired
    private WebApplicationContext webApplicationContext;

    @BeforeEach
    public void setup() {
        //Init MockMvc Object and build
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void whenConfigurationIsRandom_thenSuccessIsReceived() throws Exception {
        ObjectMapper mapper = new ObjectMapper();

        String registerJsonContent = mapper.writeValueAsString(new RegisterRequest("ciao@gmail.com", "ciao123"));
        mvc.perform(post("/api/auth/register")
                .content(registerJsonContent).contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        String loginJsonContent = mapper.writeValueAsString(new LoginRequest("ciao@gmail.com", "ciao123"));
        mvc.perform(post("/api/auth/login")
                .content(loginJsonContent).contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andReturn();


        MvcResult result = mvc.perform(post("/api/games")).andReturn();
        Assertions.assertEquals(HttpStatus.OK.value(),result.getResponse().getStatus());
    }

    @Test
    public void whenUserIsNotLoggedIn_thenForbiddenRequestIsReceived() throws Exception {
        MvcResult result = mvc.perform(post("/api/games")).andReturn();
        Assertions.assertEquals(HttpStatus.FORBIDDEN.value(),result.getResponse().getStatus());
    }

    @Test
    public void givenConfigurationId_whenConfigurationIsNotPresent_thenBadRequestIsReceived() throws Exception {
        ObjectMapper mapper = new ObjectMapper();

        String registerJsonContent = mapper.writeValueAsString(new RegisterRequest("ciao@gmail.com", "ciao123"));
        mvc.perform(post("/api/auth/register")
                .content(registerJsonContent).contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        String loginJsonContent = mapper.writeValueAsString(new LoginRequest("ciao@gmail.com", "ciao123"));
        mvc.perform(post("/api/auth/login")
                .content(loginJsonContent).contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andReturn();


        MvcResult result = mvc.perform(post("/api/games")).andReturn();
        Assertions.assertEquals(HttpStatus.OK.value(),result.getResponse().getStatus());
    }
}
