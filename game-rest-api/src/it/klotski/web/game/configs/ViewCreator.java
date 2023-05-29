package it.klotski.web.game.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

/**
 * Classe per la creazione della vista del database.
 * La classe `ViewCreator` è un componente Spring `ApplicationRunner` che viene eseguito durante l'avvio dell'applicazione.
 * Si occupa di creare o sostituire la vista del database denominata "games_view".
 */
@Component
public class ViewCreator implements ApplicationRunner {

    private final JdbcTemplate jdbcTemplate;

    /**
     * Costruttore della classe ViewCreator.
     *
     * @param jdbcTemplate l'oggetto JdbcTemplate utilizzato per eseguire le query sul database.
     */
    @Autowired
    public ViewCreator(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * Metodo run che viene eseguito durante l'avvio dell'applicazione.
     *
     * @param args gli argomenti dell'applicazione.
     */
    @Override
    public void run(ApplicationArguments args) {
        jdbcTemplate.execute("CREATE OR REPLACE VIEW \"games_view\" AS " +
                "SELECT \"games\".\"id\", \"games\".\"finished\", \"games\".\"start_configuration_id\", \"games\".\"player_id\", \"games\".\"created_at\", \"games\".\"updated_at\", COUNT(\"moves\".\"id\") as \"moves\"\n" +
                "FROM \"games\" " +
                "    LEFT JOIN \"moves\" ON \"moves\".\"game_id\" = \"games\".\"id\" " +
                "GROUP BY \"games\".\"id\" ");
    }
}
