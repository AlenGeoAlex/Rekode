package me.alenalex.rekode.models;

import me.alenalex.rekode.abstractions.models.IRekodeUser;
import me.alenalex.rekode.base.contracts.entities.Metadata;
import me.alenalex.rekode.base.structs.WorldPoint;
import me.alenalex.rekode.gen.entities.tables.pojos.Users;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

public class RekodeUser implements IRekodeUser {

    private final Users user;

    public RekodeUser(Users user) {
        this.user = user;
    }

    @Override
    public @NotNull UUID userId() {
        return this.user.getUserId();
    }

    @Override
    public @NotNull String cachedName() {
        return this.user.getCachedName();
    }

    @Override
    public @NotNull Metadata metadata() {
        return this.user.getMetadata();
    }

    @Override
    public @NotNull LocalDateTime firstLogin() {
        return this.user.getFirstLogin();
    }

    @Override
    public @NotNull LocalDateTime lastLogin() {
        return this.user.getLastLogin();
    }

    @Override
    public @NotNull Optional<Location> lastLocation() {
        WorldPoint lastLocation = this.user.getLastLocation();
        if(lastLocation == null) return Optional.empty();

        World world = Bukkit.getWorld(lastLocation.worldName());
        if(world == null) return Optional.empty();

        return Optional.of(new Location(world, lastLocation.point().x(), lastLocation.point().y(), lastLocation.point().z()));
    }

    @Override
    public @NotNull String notes() {
        return this.user.getNotes();
    }

    @Override
    public void notes(@NotNull String notes) {
        this.user.setNotes(notes);
    }

    /**
     * Updates the user's last known location.
     *
     * @param location the new location to set for the user, must not be null
     */
    public void lastLocation(@NotNull Location location){
        this.user.setLastLocation(WorldPoint.from(location.getWorld().getName(), location.getX(), location.getY(), location.getZ()));
    }
}
