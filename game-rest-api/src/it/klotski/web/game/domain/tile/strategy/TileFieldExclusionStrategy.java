package it.klotski.web.game.domain.tile.strategy;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;

/**
 * Strategia di esclusione per l'esclusione di un campo specifico durante la serializzazione JSON.
 * Implementa l'interfaccia {@link ExclusionStrategy} di Gson.
 */
public class TileFieldExclusionStrategy implements ExclusionStrategy {
    private final String fieldNameToExclude;

    /**
     * Crea un'istanza della strategia di esclusione specificando il nome del campo da escludere.
     *
     * @param fieldNameToExclude Il nome del campo da escludere durante la serializzazione JSON.
     */
    public TileFieldExclusionStrategy(String fieldNameToExclude) {
        this.fieldNameToExclude = fieldNameToExclude;
    }

    /**
     * Determina se il campo specificato deve essere escluso durante la serializzazione JSON.
     *
     * @param f I metadati del campo.
     * @return true se il campo deve essere escluso, false altrimenti.
     */
    @Override
    public boolean shouldSkipField(FieldAttributes f) {
        return f.getName().equals(fieldNameToExclude);
    }

    /**
     * Determina se la classe specificata deve essere esclusa durante la serializzazione JSON.
     *
     * @param clazz La classe da verificare.
     * @return true se la classe deve essere esclusa, false altrimenti.
     */
    @Override
    public boolean shouldSkipClass(Class<?> clazz) {
        return false;
    }
}
