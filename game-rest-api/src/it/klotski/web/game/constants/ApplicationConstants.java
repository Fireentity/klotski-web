package it.klotski.web.game.constants;

import it.klotski.web.game.domain.tile.ITile;
import it.klotski.web.game.domain.tile.RectangularTile;
import it.klotski.web.game.domain.tile.WinningTile;
import it.klotski.web.game.utils.RuntimeTypeAdapterFactory;

import java.text.SimpleDateFormat;

/**
 * Costanti utilizzate nell'applicazione.
 * La classe `ApplicationConstants` contiene le costanti utilizzate nell'applicazione, come la dimensione predefinita della pagina per la paginazione,
 * il formato della data utilizzato, il percorso dei file di configurazione, e la factory di runtime per il tipo di adattatore per le istanze di `ITile`.
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

    /**
     * Il percorso del file delle soluzioni.
     */
    public static final String SOLUTIONS_FILE_PATH = "classpath:config/solutions.json";

    /**
     * Il percorso del file delle configurazioni di partenza.
     */
    public static final String START_CONFIGURATIONS_FILE_PATH = "classpath:config/startConfigurations.json";

    /**
     * Factory di runtime per il tipo di adattatore per le istanze di `ITile`.
     */
    public static final RuntimeTypeAdapterFactory<ITile> ADAPTER_FACTORY = RuntimeTypeAdapterFactory.of(ITile.class, "type")
            .registerSubtype(RectangularTile.class, "rectangular")
            .registerSubtype(WinningTile.class, "winning");
}
