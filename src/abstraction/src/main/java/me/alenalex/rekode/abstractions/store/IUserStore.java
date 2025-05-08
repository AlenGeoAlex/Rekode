package me.alenalex.rekode.abstractions.store;

import me.alenalex.rekode.gen.entities.tables.pojos.Users;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public interface IUserStore {

    /**
     * Retrieves an IRekodeUser by their unique identifier.
     *
     * @param userId the unique identifier of the user to retrieve; must not be null
     * @return a CompletableFuture containing an Optional that may contain the IRekodeUser if found,
     *         or an empty Optional if no user with the specified ID is found
     */
    @NotNull CompletableFuture<@NotNull Optional<Users>> getUserByIdAsync(@NotNull UUID userId);

    /**
     * Creates a new user in the system.
     *
     * @param user the user object to be created; must not be null
     * @return a CompletableFuture containing a boolean indicating the success or failure of the user creation process
     */
    @NotNull CompletableFuture<@NotNull Boolean> createUserAsync(@NotNull Users user);

    /**
     * Updates the details of an existing user in the system.
     *
     * @param user the user object containing updated information; must not be null
     * @return a CompletableFuture containing a boolean indicating whether the user was successfully updated
     */
    @NotNull CompletableFuture<@NotNull Boolean> updateUserAsync(@NotNull Users user);

    /**
     * Deletes a user identified by their unique identifier.
     *
     * @param userId the unique identifier of the user to delete; must not be null
     * @return a CompletableFuture containing a Boolean indicating the success or failure of the delete operation
     */
    @NotNull CompletableFuture<@NotNull Boolean> deleteUserAsync(@NotNull UUID userId);
}
