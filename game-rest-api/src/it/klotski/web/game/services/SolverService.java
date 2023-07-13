package it.klotski.web.game.services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import it.klotski.web.game.configs.Movement;
import it.klotski.web.game.domain.tile.ITile;
import it.klotski.web.game.domain.tile.strategy.RectangularTileSearchStrategy;
import it.klotski.web.game.domain.tile.strategy.TileFieldExclusionStrategy;
import it.klotski.web.game.domain.tile.visitor.ITileVisitor;
import it.klotski.web.game.domain.tile.visitor.RectangularTileVisitor;
import it.klotski.web.game.domain.tile.visitor.WinningTileVisitor;
import it.klotski.web.game.exceptions.SolutionNotFoundException;
import it.klotski.web.game.exceptions.TileNotFoundException;
import it.klotski.web.game.payload.reponses.SolveResponse;
import it.klotski.web.game.payload.requests.SolveRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;

@Service
public class SolverService implements ISolverService {
    private final HashMap<String, Movement> solutions;

    @Autowired
    public SolverService(HashMap<String, Movement> solutions) {
        this.solutions = solutions;
    }

    @Override
    public SolveResponse nextBestMove(SolveRequest solveRequest) {
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
        List<ITileVisitor> visitors = List.of(new RectangularTileVisitor(rectangularTileStrategy),
                new WinningTileVisitor(rectangularTileStrategy));
        for (ITile tile : solveRequest.getTiles()) {
            visitors.forEach(tile::accept);
        }
        ITile tile = rectangularTileStrategy.getTile().orElseThrow(TileNotFoundException::new);
        return new SolveResponse(tile, movement.getDirection());
    }
}
