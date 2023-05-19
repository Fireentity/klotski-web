package it.klotski.web.game.config;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import it.klotski.web.game.payload.responses.MoveResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;

import static it.klotski.web.game.constants.ApplicationConstants.*;

@Component
public class FileConfigurationLoader {
    private final ResourceLoader resourceLoader;

    public FileConfigurationLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @Bean
    private HashMap<String, MoveResponse> configurations() throws IOException {
        Gson gson = new Gson();
        File file = resourceLoader.getResource(SOLUTIONS_FILE_PATH).getFile();
        return gson.fromJson(Files.readString(file.toPath()), new TypeToken<HashMap<String, MoveResponse>>(){}.getType());
    }
}
