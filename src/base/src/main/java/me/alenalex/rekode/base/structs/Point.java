package me.alenalex.rekode.base.structs;

import me.alenalex.rekode.base.IRekodeBase;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

/**
 * Defines a point irrespective of the world
 * @param x X of the point
 * @param y Y of the point
 * @param z Z of the point
 */
public record Point(double x, double y, double z) {

    /**
     * Converts the x-coordinate of the point to an integer by casting.
     *
     * @return The x-coordinate of the point, cast as an integer.
     */
    public int asXInt(){
        return (int) x;
    }

    /**
     * Converts the Y-coordinate of the point to an integer.
     * This method performs a type cast from double to int, truncating any decimal portion.
     *
     * @return The Y-coordinate of the point as an integer.
     */
    public int asYInt(){
        return (int) y;
    }

    /**
     * Converts the Z-coordinate of the point to an integer value.
     * The conversion involves casting the double value to an integer.
     *
     * @return Integer representation of the Z-coordinate of the point.
     */
    public int asZInt(){
        return (int) z;
    }

    /**
     * Converts the point instance into a JSON String
     * @return Valid JSON String
     */
    @NotNull
    public String toJson(){
        return IRekodeBase.get().jsonProvider().toJsonString(this);
    }

    /**
     * Convert a valid JSON string into a Point
     * @param jsonString Valid JSON String
     * @return An optional Point
     */
    @NotNull
    public static Optional<Point> fromJson(@NotNull String jsonString){
        return Optional.ofNullable(IRekodeBase.get().jsonProvider().fromJsonString(jsonString, Point.class));
    }

}
