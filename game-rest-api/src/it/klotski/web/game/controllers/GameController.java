package it.klotski.web.game.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.klotski.web.game.domain.Game;
import it.klotski.web.game.services.IPuzzleService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/games")
@RequiredArgsConstructor
public class GameController {
    private final IPuzzleService puzzleService;

    @PostMapping
    public ResponseEntity<String> startGame(@RequestParam(required = false) Integer startConfigId) throws JsonProcessingException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        ObjectMapper objectMapper = new ObjectMapper();
        if(startConfigId == null) {
            return ResponseEntity.ok(objectMapper.writeValueAsString(puzzleService.createGameFromRandomConfiguration(authentication.getName())));
        }
        return ResponseEntity.ok(objectMapper.writeValueAsString(puzzleService.createGameFromConfiguration(authentication.getName(), startConfigId)));
    }

    @GetMapping
    public ResponseEntity<List<Game>> getGames(@RequestParam(defaultValue = "0") int page,
                                               @RequestParam(defaultValue = "10") int size) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return ResponseEntity.ok(puzzleService.findGamesByUser(authentication.getName(), PageRequest.of(page, size)));
    }
}
