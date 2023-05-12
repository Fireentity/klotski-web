package it.klotski.web.game.controllers;

import it.klotski.web.game.payload.reponses.GameInfoResponse;
import it.klotski.web.game.services.user.IPuzzleService;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/api/game/info")
public class GameInfoController {
    private final IPuzzleService puzzleService;

    public GameInfoController(IPuzzleService puzzleService) {
        this.puzzleService = puzzleService;
    }

    @GetMapping
    public ResponseEntity<List<GameInfoResponse>> getGames(@RequestParam(defaultValue = "0") int page,
                                                           @RequestParam(defaultValue = "10") int size) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return ResponseEntity.ok(puzzleService.findGamesByUser(authentication.getName(), PageRequest.of(page, size))
                .stream()
                .map(GameInfoResponse::from)
                .collect(Collectors.toList()));
    }
}
