package it.klotski.solutions.configurer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.util.DigestUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.TreeSet;

@Slf4j
@SpringBootApplication(scanBasePackages = {"it.klotski.solutions.configurer.*"})
public class SolutionsConfigurerApplication {
    private static final String SEPARATOR = " ";
    private static final int PATH_ARGUMET_INDEX = 0;
    private static final String FIELD_TO_EXCLUDE = "id";
    private static final GsonBuilder GSON_BUILDER = new GsonBuilder().registerTypeAdapterFactory(RuntimeTypeAdapterFactory.of(ITile.class, "type")
            .registerSubtype(WinningTile.class, "winning")
            .registerSubtype(RectangularTile.class, "rectangular"));
    private static final Gson DESERIALIZER = GSON_BUILDER.create();
    private static final Gson SERIALIZER = GSON_BUILDER.setExclusionStrategies(new TileFieldExclusionStrategy(FIELD_TO_EXCLUDE))
            .create();

    public static void main(String[] args) throws IOException {
        if (args.length != 1) {
            throw new IllegalArgumentException(String.format("Invalid argument. A path must be provided instead of %s", String.join(SEPARATOR, args)));
        }
        String path = args[PATH_ARGUMET_INDEX];

        String json = Files.readString(Paths.get(path), StandardCharsets.UTF_8);
        TreeSet<ITile> tiles = DESERIALIZER.fromJson(json, new TypeToken<TreeSet<ITile>>() {}.getType());
        String boardHash = DigestUtils.md5DigestAsHex(SERIALIZER.toJson(tiles).getBytes(StandardCharsets.UTF_8));
        System.out.println(boardHash);
    }
}

