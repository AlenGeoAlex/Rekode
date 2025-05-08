package me.alenalex.rekode.abstractions.store;

import me.alenalex.rekode.gen.entities.tables.pojos.Mines;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public interface IMineStore {

    /**
     * Retrieves a specific mine by its unique identifier.
     *
     * @param mineId the unique identifier of the mine to retrieve; must not be null
     * @return a CompletableFuture containing an Optional that may contain the Mines object if found,
     *         or an empty Optional if no mine with the specified ID exists
     */
    @NotNull CompletableFuture<@NotNull Optional<Mines>> getMineByIdAsync(@NotNull UUID mineId);

    /**
     * Asynchronously creates a new mine in the system.
     *
     * @param mine the mine object to be created; must not be null
     * @return a CompletableFuture containing a boolean indicating the success or failure of the mine creation process
     */
    @NotNull CompletableFuture<@NotNull Boolean> createMineAsync(@NotNull Mines mine);

    /**
     * Asynchronously creates multiple mines in the system.
     *
     * @param mines an iterable collection of Mines objects to be created; must not be null
     * @return a CompletableFuture containing a boolean indicating the success or failure
     *         of the creation process for all provided mines
     */
    @NotNull CompletableFuture<@NotNull Boolean> createMinesAsync(@NotNull Iterable<Mines> mines);

    /**
     * Asynchronously updates an existing mine with new information.
     *
     * @param mine the Mines object containing updated data; must not be null
     * @return a CompletableFuture containing a boolean indicating whether the update operation was successful
     */
    @NotNull CompletableFuture<@NotNull Boolean> updateMineAsync(@NotNull Mines mine);

    /**
     * Asynchronously deletes a mine identified by its unique identifier.
     *
     * @param mineId the unique identifier of the mine to delete; must not be null
     * @return a CompletableFuture containing a Boolean indicating whether the deletion was successful
     */
    @NotNull CompletableFuture<@NotNull Boolean> deleteMineAsync(@NotNull UUID mineId);
}
