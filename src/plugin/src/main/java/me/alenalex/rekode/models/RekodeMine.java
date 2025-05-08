package me.alenalex.rekode.models;

import me.alenalex.rekode.abstractions.models.IRekodeMine;
import me.alenalex.rekode.base.contracts.entities.Metadata;
import me.alenalex.rekode.base.structs.Point;
import me.alenalex.rekode.gen.entities.tables.pojos.Mines;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class RekodeMine implements IRekodeMine {

    private final Mines mines;

    public RekodeMine(Mines mines) {
        this.mines = mines;
    }

    @Override
    public @NotNull UUID mineId() {
        return this.mines.getMineId();
    }

    @Override
    public @NotNull String name() {
        return this.mines.getName();
    }

    @Override
    public void name(@NotNull String name) {
        Objects.requireNonNull(name, "name cannot be null");
        this.mines.setName(name);
    }

    @Override
    public @NotNull String mineType() {
        return this.mines.getMineType();
    }

    @Override
    public @NotNull Point minPoint() {
        return this.mines.getMinPoint();
    }

    @Override
    public @NotNull Point maxPoint() {
        return this.mines.getMaxPoint();
    }

    @Override
    public @NotNull Metadata metadata() {
        return this.mines.getMetadata();
    }

    @Override
    public @NotNull CompletableFuture<Boolean> hasOwnerAsync() {
        //TODO
        return CompletableFuture.completedFuture(false);
    }
}
