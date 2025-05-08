package me.alenalex.rekode.abstractions.store;

import me.alenalex.rekode.gen.entities.tables.pojos.MineUsers;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public interface IMineUserStore {

    /**
     * Asynchronously retrieves the mine-user relationship information for a specific user.
     *
     * @param userId the unique identifier of the user whose mine-user details are to be retrieved; must not be null
     * @return a CompletableFuture containing an Optional that may include the MineUsers object if found,
     *         or an empty Optional if no mine-user relationship exists for the specified user
     */
    @NotNull CompletableFuture<Optional<MineUsers>> getOwnedMineOfUserAsync(@NotNull UUID userId);

    /**
     * Asynchronously retrieves a list of all members associated with a specific mine.
     *
     * @param mineId the unique identifier of the mine whose members are to be retrieved; must not be null
     * @return a CompletableFuture containing a list of MineUsers objects representing the members of the specified mine
     */
    @NotNull CompletableFuture<List<MineUsers>> getMembersOfMineAsync(@NotNull UUID mineId);

    /**
     * Asynchronously creates a new mine-user association in the system.
     *
     * @param mineUser the MineUsers object representing the mine-user relationship to be created; must not be null
     * @return a CompletableFuture containing a Boolean indicating the success or failure of the creation process
     */
    @NotNull CompletableFuture<Boolean> createMineUserAsync(@NotNull MineUsers mineUser);

    /**
     * Asynchronously updates the details of an existing mine-user relationship in the system.
     *
     * @param mineUser the MineUsers object containing the updated mine-user relationship details; must not be null
     * @return a CompletableFuture containing a Boolean indicating the success or failure of the update operation
     */
    @NotNull CompletableFuture<Boolean> updateMineUserAsync(@NotNull MineUsers mineUser);

    /**
     * Asynchronously deletes a mine-user relationship identified by its unique identifier.
     *
     * @param mineUserId the unique identifier of the mine-user relationship to delete; must not be null
     * @return a CompletableFuture containing a Boolean indicating whether the deletion was successful
     */
    @NotNull CompletableFuture<Boolean> deleteMineUserAsync(@NotNull UUID mineUserId);
}
