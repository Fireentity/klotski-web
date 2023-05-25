package it.klotski.web.game.controllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import it.klotski.web.game.configs.Movement;
import it.klotski.web.game.domain.tile.ITile;
import it.klotski.web.game.domain.tile.strategy.RectangularTileSearchStrategy;
import it.klotski.web.game.domain.tile.strategy.TileFieldExclusionStrategy;
import it.klotski.web.game.domain.tile.visitor.ITileVisitor;
import it.klotski.web.game.domain.tile.visitor.RectangularTileVisitor;
import it.klotski.web.game.exceptions.SolutionNotFoundException;
import it.klotski.web.game.payload.reponses.SolveResponse;
import it.klotski.web.game.payload.requests.SolveRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping(path = "/api/solver")
public class SolveController {

    private final HashMap<String, Movement> solutions;

    @Autowired
    public SolveController(HashMap<String, Movement> solutions) {
        this.solutions = solutions;
    }

    @PostMapping
    public ResponseEntity<SolveResponse> nextBestMove(@RequestBody SolveRequest solveRequest) {
        String fieldNameToExclude = "id";
        Gson gson = new GsonBuilder()
                .setExclusionStrategies(new TileFieldExclusionStrategy(fieldNameToExclude))
                .create();
        String boardHash = DigestUtils.md5DigestAsHex(gson.toJson(solveRequest.getTiles()).getBytes(StandardCharsets.UTF_8));
        Movement movement = solutions.get(boardHash);
        if (movement == null) {
            throw new SolutionNotFoundException();
        }
        RectangularTileSearchStrategy rectangularTileStrategy = new RectangularTileSearchStrategy(movement.getX(), movement.getY());
        List<ITileVisitor> visitors = List.of(new RectangularTileVisitor(rectangularTileStrategy));
        for(ITile tile : solveRequest.getTiles()) {
            visitors.forEach(tile::accept);
        }
        ITile tile = rectangularTileStrategy.getTile().orElseThrow(() -> new IllegalStateException(String.format("Unable to find tile with x:%d y:%d",
                movement.getX(),
                movement.getY())));
        return ResponseEntity.ok(SolveResponse.from(tile, movement));
    }
}
