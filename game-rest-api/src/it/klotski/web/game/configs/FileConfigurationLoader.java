package it.klotski.web.game.configs;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;

import static it.klotski.web.game.constants.ApplicationConstants.*;

/**
 * Configurazione per la lettura dei dati di configurazione da un file.
 */
@Component
public class FileConfigurationLoader {
    private final ResourceLoader resourceLoader;

    /**
     * Costruttore della classe FileConfigurationLoader.
     *
     * @param resourceLoader Il caricatore di risorse utilizzato per ottenere il percorso del file di configurazione.
     */
    public FileConfigurationLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    /**
     * Metodo che restituisce una lista di configurazioni di tabelloni lette da un file.
     *
     * @return La lista di configurazioni di tabelloni.
     * @throws IOException Se si verifica un errore durante la lettura del file.
     */
    @Bean
    private List<Board> boardConfigurations() throws IOException {
        Gson gson = new GsonBuilder().registerTypeAdapterFactory(ADAPTER_FACTORY).create();
        File file = resourceLoader.getResource(START_CONFIGURATIONS_FILE_PATH).getFile();
        return gson.fromJson(Files.readString(file.toPath()), new TypeToken<List<Board>>(){}.getType());
    }

    /**
     * Metodo che restituisce una mappa di configurazioni di soluzioni lette da un file.
     *
     * @return La mappa di configurazioni di soluzioni.
     * @throws IOException Se si verifica un errore durante la lettura del file.
     */
    @Bean
    private HashMap<String, Movement> solutionsConfigurations() throws IOException {
        Gson gson = new Gson();
        File file = resourceLoader.getResource(SOLUTIONS_FILE_PATH).getFile();
        return gson.fromJson(Files.readString(file.toPath()), new TypeToken<HashMap<String, Movement>>(){}.getType());
    }
}
