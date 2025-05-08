package me.alenalex.rekode.abstractions.models;

import me.alenalex.rekode.base.contracts.entities.Metadata;
import me.alenalex.rekode.base.structs.Point;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public interface IRekodeMine {

    @NotNull UUID mineId();

    @NotNull String name();

    void name(@NotNull String name);

    @NotNull String mineType();

    @NotNull Point minPoint();

    @NotNull Point maxPoint();

    @NotNull Metadata metadata();

    @NotNull CompletableFuture<Boolean> hasOwnerAsync();


}
