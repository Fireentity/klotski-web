package it.klotski.web.game.utils;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;

public class ExcludeFieldExclusionStrategy implements ExclusionStrategy {
    private final String fieldNameToExclude;

    public ExcludeFieldExclusionStrategy(String fieldNameToExclude) {
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
