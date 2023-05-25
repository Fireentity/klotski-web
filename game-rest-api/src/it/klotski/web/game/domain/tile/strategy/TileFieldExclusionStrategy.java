package it.klotski.web.game.domain.tile.strategy;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;

public class TileFieldExclusionStrategy implements ExclusionStrategy {
    private final String fieldNameToExclude;

    public TileFieldExclusionStrategy(String fieldNameToExclude) {
        this.fieldNameToExclude = fieldNameToExclude;
    }

    @Override
    public boolean shouldSkipField(FieldAttributes f) {
        return f.getName().equals(fieldNameToExclude);
    }

    @Override
    public boolean shouldSkipClass(Class<?> clazz) {
        return false;
    }
}
