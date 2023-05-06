package it.klotski.web.game;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication(scanBasePackages = {"it.klotski.web.game.*"})
public class GameRestApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(GameRestApiApplication.class, args);
    }
}
