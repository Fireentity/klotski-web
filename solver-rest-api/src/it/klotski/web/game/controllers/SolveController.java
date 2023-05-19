package it.klotski.web.game.controllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import it.klotski.web.game.exceptions.SolutionNotFoundException;
import it.klotski.web.game.payload.requests.SolveRequest;
import it.klotski.web.game.payload.responses.MoveResponse;
import it.klotski.web.game.tile.ITile;
import it.klotski.web.game.utils.ExcludeFieldExclusionStrategy;
import it.klotski.web.game.utils.Movement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;

@RestController
@RequestMapping(path = "/api/solver")
public class SolveController {

    private final HashMap<String, Movement> solutions;

    @Autowired
    public SolveController(HashMap<String, Movement> solutions) {
        this.solutions = solutions;
    }

    @PostMapping
    public MoveResponse nextBestMove(SolveRequest solveRequest) {
        //TODO vedere se è giusto
        String fieldNameToExclude = "id";
        Gson gson = new GsonBuilder()
                .setExclusionStrategies(new ExcludeFieldExclusionStrategy(fieldNameToExclude))
                .create();
        String boardHash = DigestUtils.md5DigestAsHex(gson.toJson(solveRequest.getTiles()).getBytes(StandardCharsets.UTF_8));
        Movement movement = solutions.get(boardHash);
        if (movement == null) {
            throw new SolutionNotFoundException();
        }
        for (ITile tile : solveRequest.getTiles()) {
            //TODO come avere la posizione dei tile
            if (movement.getX() == tile && movement.getY() == tile) {
                return MoveResponse.from(tile, movement);
            }
        }
        throw new IllegalStateException(String.format("Unable to find tile with x:%d y:%d", movement.getX(), movement.getY()));
    }


}
