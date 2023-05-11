package it.klotski.web.game.constants;

import it.klotski.web.game.domain.tile.ITile;
import it.klotski.web.game.domain.tile.RectangularTile;
import it.klotski.web.game.domain.tile.WinningTile;
import it.klotski.web.game.utils.RuntimeTypeAdapterFactory;

import java.text.SimpleDateFormat;

public class ApplicationConstants {
    public static final int DEFAULT_PAGE_SIZE = 20;
    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy");
    public static final String START_CONFIGURATIONS_FILE_PATH = "classpath:config/startConfigurations.json";
    public static final RuntimeTypeAdapterFactory<ITile> ADAPTER_FACTORY = RuntimeTypeAdapterFactory.of(ITile.class, "type")
            .registerSubtype(RectangularTile.class, "rectangular")
            .registerSubtype(WinningTile.class, "winning");
}
