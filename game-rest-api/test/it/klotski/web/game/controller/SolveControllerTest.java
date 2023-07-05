package it.klotski.web.game.controller;

import com.google.gson.Gson;
import it.klotski.web.game.configs.Board;
import it.klotski.web.game.controllers.SolveController;
import it.klotski.web.game.domain.move.Direction;
import it.klotski.web.game.domain.tile.ITile;
import it.klotski.web.game.domain.tile.strategy.RectangularTileSearchStrategy;
import it.klotski.web.game.domain.tile.visitor.ITileVisitor;
import it.klotski.web.game.domain.tile.visitor.RectangularTileVisitor;
import it.klotski.web.game.domain.tile.visitor.WinningTileVisitor;
import it.klotski.web.game.payload.reponses.SolveResponse;
import it.klotski.web.game.payload.requests.SolveRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;
import java.util.TreeSet;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class SolveControllerTest {
    private MockMvc mvc;
    @Autowired
    private List<Board> boards;
    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    private Gson gson;
    @Autowired
    private SolveController solveController;
    private static final int GAME_CONFIGURATION_ID = 0;

    @BeforeEach
    public void setup() {
        //Init MockMvc Object and build
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(SecurityMockMvcConfigurers.springSecurity())
                .build();
    }

    @Test
    @WithMockUser(username = "example@gmail.com")
    public void givenSolvableConfiguration_whenCalculatingNextBestMove_thanSolveResponseIsReturned() throws Exception {
        TreeSet<ITile> tiles = boards.get(GAME_CONFIGURATION_ID).getTiles();
        RectangularTileSearchStrategy searchStrategy = new RectangularTileSearchStrategy(0,0);
        List<ITileVisitor> tileVisitors = List.of(new RectangularTileVisitor(searchStrategy), new WinningTileVisitor(searchStrategy));
        for(ITile tile : tiles) {
            tileVisitors.forEach(tile::accept);
        }
        ITile tile = searchStrategy.getTile().orElseThrow();
        MvcResult result = mvc.perform(post("/api/solver")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(new SolveRequest(boards.get(GAME_CONFIGURATION_ID).getTiles())))
                .with(csrf())).andReturn();
        SolveResponse solveResponse = gson.fromJson(result.getResponse().getContentAsString(), SolveResponse.class);
        Assertions.assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
        Assertions.assertEquals(solveResponse.getDirection(), Direction.DOWN);
        Assertions.assertEquals(solveResponse.getTile(), tile);
    }
}
