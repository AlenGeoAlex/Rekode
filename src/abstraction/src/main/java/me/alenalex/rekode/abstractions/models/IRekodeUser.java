package me.alenalex.rekode.abstractions.models;

import me.alenalex.rekode.base.contracts.entities.Metadata;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

public interface IRekodeUser {

    @NotNull UUID userId();

    @NotNull String cachedName();

    @NotNull Metadata metadata();

    @NotNull LocalDateTime firstLogin();

    @NotNull LocalDateTime lastLogin();

    @NotNull Optional<Location> lastLocation();

    @NotNull String notes();

    void notes(@NotNull String notes);


}
