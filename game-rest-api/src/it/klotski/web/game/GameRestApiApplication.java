package it.klotski.web.game;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication(scanBasePackages = {"it.klotski.web.game.*"})

/**
 * Classe principale dell'applicazione REST API del gioco Klotski.
 *
 * La classe GameRestApiApplication è la classe principale dell'applicazione REST API del gioco Klotski.
 * Avvia l'applicazione Spring Boot e gestisce l'avvio del server.
 */
public class GameRestApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(GameRestApiApplication.class, args);
    }
}
