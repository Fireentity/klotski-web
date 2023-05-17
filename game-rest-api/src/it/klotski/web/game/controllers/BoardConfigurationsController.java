package it.klotski.web.game.controllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import it.klotski.web.game.configs.Board;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import static it.klotski.web.game.constants.ApplicationConstants.ADAPTER_FACTORY;
import static it.klotski.web.game.constants.ApplicationConstants.START_CONFIGURATIONS_FILE_PATH;

/**
 * Il controller che gestisce le richieste relative alle configurazioni d'inizio delle board.
 * Fornisce un endpoint per ottenere la lista delle configurazioni d'inizio disponibili.
 */

@RestController
@RequestMapping("/api/configurations")
public class BoardConfigurationsController {
    private final List<Board> boards;

    /**
     * Costruisce un'istanza di BoardConfigurationsController.
     *
     * @param resourceLoader il ResourceLoader utilizzato per caricare le configurazioni d'inizio
     * @throws IOException se si verifica un errore durante la lettura del file di configurazione
     */
    public BoardConfigurationsController(ResourceLoader resourceLoader) throws IOException {
        Gson gson = new GsonBuilder().registerTypeAdapterFactory(ADAPTER_FACTORY).create();
        File file = resourceLoader.getResource(START_CONFIGURATIONS_FILE_PATH).getFile();
        this.boards = gson.fromJson(Files.readString(file.toPath()), new TypeToken<List<Board>>(){}.getType());
    }

    /**
     * Restituisce una ResponseEntity contenente la lista delle configurazioni d'inizio delle board.
     *
     * @return una ResponseEntity contenente la lista delle configurazioni d'inizio delle board
     */
    @GetMapping
    public ResponseEntity<List<Board>> getStartConfigurations() {
        return ResponseEntity.ok(boards);
    }
}
