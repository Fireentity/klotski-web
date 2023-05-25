package it.klotski.web.game.constants;

import it.klotski.web.game.domain.tile.ITile;
import it.klotski.web.game.domain.tile.RectangularTile;
import it.klotski.web.game.domain.tile.WinningTile;
import it.klotski.web.game.utils.RuntimeTypeAdapterFactory;

import java.text.SimpleDateFormat;

/**
 * Questa classe contiene le costanti utilizzate nell'applicazione.
 */
public class ApplicationConstants {

    /**
     * La dimensione predefinita della pagina utilizzata per la paginazione.
     */
    public static final int DEFAULT_PAGE_SIZE = 20;

    /**
     * Il formato della data utilizzato nell'applicazione.
     */
    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");

    public static final String SOLUTIONS_FILE_PATH = "classpath:config/solutions.json";

    /**
     * Il percorso del file di configurazione delle configurazioni di partenza.
     */
    public static final String START_CONFIGURATIONS_FILE_PATH = "classpath:config/startConfigurations.json";

    /**
     * Factory di runtime per il tipo di adattatore per le istanze di ITile.
     */
    public static final RuntimeTypeAdapterFactory<ITile> ADAPTER_FACTORY = RuntimeTypeAdapterFactory.of(ITile.class, "type")
            .registerSubtype(RectangularTile.class, "rectangular")
            .registerSubtype(WinningTile.class, "winning");
}
