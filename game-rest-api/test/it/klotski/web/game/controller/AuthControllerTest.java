package it.klotski.web.game.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import it.klotski.web.game.domain.user.User;
import it.klotski.web.game.payload.requests.LoginRequest;
import it.klotski.web.game.payload.requests.RegisterRequest;
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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class AuthControllerTest {
    private MockMvc mvc;
    @MockBean
    private IUserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private WebApplicationContext webApplicationContext;

    @BeforeEach
    public void setup() {
        //Init MockMvc Object and build
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void givenUserIsNotRegistered_whenRegistering_thenSuccessIsReceived() throws Exception {
        Mockito.when(userRepository.existsUserByEmail("example@gmail.com")).thenReturn(false);
        RegisterRequest registerRequest = new RegisterRequest("example@gmail.com", "password");

        String jsonContent = new Gson().toJson(registerRequest);
        MvcResult result = mvc.perform(post("/api/auth/register")
                .content(jsonContent).contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andReturn();
    }

    @Test
    public void givenUserIsRegistered_whenRegistering_thenConflictIsReceived() throws Exception {
        Mockito.when(userRepository.existsUserByEmail("example@gmail.com")).thenReturn(true);
        String jsonContent = new Gson().toJson(new RegisterRequest("example@gmail.com", "password"));

        MvcResult result = mvc.perform(post("/api/auth/register")
                .content(jsonContent).contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andReturn();
        Assertions.assertEquals(HttpStatus.CONFLICT.value(), result.getResponse().getStatus());
    }

    @Test
    public void givenUserIsRegistered_whenLogin_thenSuccessIsReceived() throws Exception {
        User user = new User();
        user.setId(1);
        user.setEmail("example@gmail.com");
        user.setPassword(passwordEncoder.encode("password"));
        Mockito.when(userRepository.findByEmail("example@gmail.com")).thenReturn(Optional.of(user));
        String jsonContent = new ObjectMapper().writeValueAsString(new LoginRequest("example@gmail.com", "password"));

        MvcResult result = mvc.perform(post("/api/auth/login")
                .content(jsonContent).contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andReturn();
        Assertions.assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
    }

    @Test
    public void givenUserIsNotPresent_whenLogin_thenUnauthorizedIsReceived() throws Exception {
        Mockito.when(userRepository.findByEmail("example@gmail.com")).thenReturn(Optional.empty());
        Gson gson = new Gson();

        String loginJsonContent = gson.toJson(new LoginRequest("ciao@gmail.com", "ciao123"));
        MvcResult result = mvc.perform(post("/api/auth/login")
                .content(loginJsonContent).contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andReturn();
        Assertions.assertEquals(HttpStatus.UNAUTHORIZED.value(), result.getResponse().getStatus());
    }
}
