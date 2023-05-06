package it.klotski.web.game.constants;

import it.klotski.web.game.domain.tile.ITile;
import it.klotski.web.game.domain.tile.RectangularTile;
import it.klotski.web.game.utils.RuntimeTypeAdapterFactory;

public class ApplicationConstants {
    public static final String START_CONFIGURATIONS_FILE_PATH = "classpath:config/startConfigurations.json";
    public static final RuntimeTypeAdapterFactory<ITile> ADAPTER_FACTORY = RuntimeTypeAdapterFactory.of(ITile.class, "type")
            .registerSubtype(RectangularTile.class, "rectangular");
}
