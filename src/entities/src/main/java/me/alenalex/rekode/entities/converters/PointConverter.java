package me.alenalex.rekode.entities.converters;

import me.alenalex.rekode.base.structs.Point;
import org.jooq.Converter;

public class PointConverter implements Converter<String, Point> {

    public PointConverter() {
        // Default constructor required by jOOQ
    }

    @Override
    public Point from(String s) {
        if (s == null || s.isEmpty()) {
            return null;
        }
        return Point.fromJson(s).orElse(null);
    }

    @Override
    public String to(Point point) {
        return point == null ? null : point.toJson();
    }

    @Override
    public Class<String> fromType() {
        return String.class;
    }

    @Override
    public Class<Point> toType() {
        return Point.class;
    }
}