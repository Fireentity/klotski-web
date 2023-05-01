package it.klotski.web.game;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication(scanBasePackages = {"it.klotski.web.game.controllers",
        "it.klotski.web.game.services",
        "it.klotski.web.game.repositories",
        "it.klotski.web.game.domain",
        "it.klotski.web.game.security",
        "it.klotski.web.game.handler"})
public class GameRestApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(GameRestApiApplication.class, args);
    }
}
