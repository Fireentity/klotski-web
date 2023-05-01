package it.klotski.web.game.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.klotski.web.game.payload.requests.LoginRequest;
import it.klotski.web.game.payload.requests.RegisterRequest;
import it.klotski.web.game.repositories.IUserRepository;
import it.klotski.web.game.services.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class AuthControllerTest {
    private MockMvc mvc;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserService userService;
    @Autowired
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
    public void givenUser_whenUserIsNotRegistered_thenSuccessIsReceived() throws Exception {
        //Given
        RegisterRequest registerRequest = new RegisterRequest("ciao@gmail.com", "ciao123");

        ObjectMapper mapper = new ObjectMapper();
        String jsonContent = mapper.writeValueAsString(registerRequest);
        MvcResult result = mvc.perform(post("/api/auth/register")
                .content(jsonContent).contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andReturn();

        //Then
        Assertions.assertEquals("User registered successfully", result.getResponse().getContentAsString());
    }

    @Test
    public void givenUserIsNotPresent_whenRegistering_thenSuccessIsReceived() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        String jsonContent = mapper.writeValueAsString(new RegisterRequest("ciao@gmail.com", "ciao123"));
        mvc.perform(post("/api/auth/register")
                .content(jsonContent).contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        MvcResult result = mvc.perform(post("/api/auth/register")
                .content(jsonContent).contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andReturn();
        Assertions.assertEquals(HttpStatus.CONFLICT.value(), result.getResponse().getStatus());
    }

    @Test
    public void givenUserAlreadyPresent_whenRegistering_thenBadRequestIsReceived() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        String jsonContent = mapper.writeValueAsString(new RegisterRequest("ciao@gmail.com", "ciao123"));
        mvc.perform(post("/api/auth/register")
                .content(jsonContent).contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        MvcResult result = mvc.perform(post("/api/auth/register")
                .content(jsonContent).contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andReturn();
        Assertions.assertEquals(HttpStatus.CONFLICT.value(), result.getResponse().getStatus());
    }

    @Test
    public void givenUserIsPresent_whenLogin_thenSuccessIsReceived() throws Exception {
        String registerJsonContent = new ObjectMapper().writeValueAsString(new RegisterRequest("ciao@gmail.com", "ciao123"));
        mvc.perform(post("/api/auth/register")
                .content(registerJsonContent).contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        String loginJsonContent = new ObjectMapper().writeValueAsString(new LoginRequest("ciao@gmail.com", "ciao123"));
        MvcResult result = mvc.perform(post("/api/auth/login")
                .content(loginJsonContent).contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andReturn();
        Assertions.assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
    }

    @Test
    public void givenUserIsNotPresent_whenLogin_thenSuccessIsReceived() throws Exception {
        ObjectMapper mapper = new ObjectMapper();

        String loginJsonContent = mapper.writeValueAsString(new LoginRequest("ciao@gmail.com", "ciao123"));
        MvcResult result = mvc.perform(post("/api/auth/login")
                .content(loginJsonContent).contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andReturn();
        Assertions.assertEquals(HttpStatus.UNAUTHORIZED.value(), result.getResponse().getStatus());
    }
}
