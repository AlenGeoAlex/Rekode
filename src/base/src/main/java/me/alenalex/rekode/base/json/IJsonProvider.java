package me.alenalex.rekode.base.json;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface IJsonProvider {

    /**
     * Converts to Json String
     * @param object the object to convert
     * @return a valid JSON String
     * @param <T> Type of input
     */
    @NotNull
    <T> String toJsonString(@NotNull T object);

    /**
     * Converts a string back to its object
     * @param jsonString a valid JSON String
     * @param classOfT Type of the concrete class
     * @return the constructed object
     * @param <T> Type of object
     */
    @Nullable <T> T fromJsonString(@NotNull String jsonString, Class<T> classOfT);

}
