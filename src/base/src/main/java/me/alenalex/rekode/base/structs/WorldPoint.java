package me.alenalex.rekode.base.structs;

import me.alenalex.rekode.base.IRekodeBase;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

/**
 * Defines a point in the world
 * @param worldName name of the world
 * @param point the referenced point
 */
public record WorldPoint(String worldName, Point point) {

    /**
     * Create a world point
     * @param worldName name of the world
     * @param x X of the point
     * @param y Y of the point
     * @param z Z of the point
     * @return A world point
     */
    @NotNull
    public static WorldPoint from(String worldName, double x, double y, double z){
        return new WorldPoint(worldName, new Point(x, y, z));
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
     * Convert a valid JSON string into a World Point
     * @param jsonString Valid JSON String
     * @return An optional Point
     */
    @NotNull
    public static Optional<WorldPoint> fromJson(@NotNull String jsonString){
        return Optional.ofNullable(IRekodeBase.get().jsonProvider().fromJsonString(jsonString, WorldPoint.class));
    }

}
