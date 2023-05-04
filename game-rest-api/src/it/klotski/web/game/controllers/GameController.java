package it.klotski.web.game.controllers;

import com.google.gson.Gson;
import it.klotski.web.game.payload.reponses.GameResponse;
import it.klotski.web.game.services.user.IPuzzleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/api/games")
public class GameController {
    private final IPuzzleService puzzleService;
    private final Gson gson;

    @Autowired
    public GameController(IPuzzleService puzzleService, Gson gson) {
        this.puzzleService = puzzleService;
        this.gson = gson;
    }

    @PostMapping
    public ResponseEntity<String> startGame(@RequestParam(required = false) Integer startConfigId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(startConfigId == null) {
            return ResponseEntity.ok(gson.toJson(puzzleService.createGameFromRandomConfiguration(authentication.getName())));
        }
        return ResponseEntity.ok(gson.toJson(puzzleService.createGameFromConfiguration(authentication.getName(), startConfigId)));
    }

    @GetMapping
    public ResponseEntity<List<GameResponse>> getGames(@RequestParam(defaultValue = "0") int page,
                                                       @RequestParam(defaultValue = "10") int size) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return ResponseEntity.ok(puzzleService.findGamesByUser(authentication.getName(), PageRequest.of(page, size))
                .stream()
                .map(GameResponse::from)
                .collect(Collectors.toList()));
    }
}
