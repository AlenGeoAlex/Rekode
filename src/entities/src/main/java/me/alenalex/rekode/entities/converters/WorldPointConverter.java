package me.alenalex.rekode.entities.converters;

import me.alenalex.rekode.base.structs.WorldPoint;
import org.jooq.Converter;

public class WorldPointConverter implements Converter<String, WorldPoint> {

    public WorldPointConverter() {
        // Default constructor required by jOOQ
    }

    @Override
    public WorldPoint from(String s) {
        if (s == null || s.isEmpty()) {
            return null;
        }
        return WorldPoint.fromJson(s).orElse(null);
    }

    @Override
    public String to(WorldPoint point) {
        return point == null ? null : point.toJson();
    }

    @Override
    public Class<String> fromType() {
        return String.class;
    }

    @Override
    public Class<WorldPoint> toType() {
        return WorldPoint.class;
    }
}
