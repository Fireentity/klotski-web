package it.klotski.web.game;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication(scanBasePackages = {"it.klotski.web.game.*"})
public class SolverRestApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(SolverRestApiApplication.class, args);
    }
}
