package me.alenalex.rekode.base.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class GsonJsonProvider implements IJsonProvider {

    private final Gson gson = new GsonBuilder()
            .serializeNulls()
            .generateNonExecutableJson()
            .create();

    @Override
    public @NotNull <T> String toJsonString(@NotNull T object) {
        return gson.toJson(object);
    }

    @Override
    public <T> @Nullable T fromJsonString(@NotNull String jsonString, Class<T> classOfT) {
        try {
            return gson.fromJson(jsonString, classOfT);
        } catch (Exception e) {
            return null;
        }
    }
}
