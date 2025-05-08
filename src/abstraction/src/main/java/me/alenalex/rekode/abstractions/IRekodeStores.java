package me.alenalex.rekode.abstractions;

import me.alenalex.rekode.abstractions.store.IMineStore;
import me.alenalex.rekode.abstractions.store.IMineUserStore;
import me.alenalex.rekode.abstractions.store.IUserStore;
import org.jetbrains.annotations.NotNull;

public interface IRekodeStores {

    /**
     * Provides access to the IUserStore interface, which allows operations such as retrieving,
     * creating, updating, and deleting users in the system.
     *
     * @return an instance of {@link IUserStore} for user-related operations
     */
    @NotNull IUserStore userStore();

    /**
     * Provides access to the IMineStore interface, which allows operations such as retrieving,
     * creating, updating, and deleting mines in the system.
     *
     * @return an instance of {@link IMineStore} for mine-related operations
     */
    @NotNull IMineStore mineStore();

    /**
     * Provides access to the IMineUserStore interface, which facilitates operations
     * such as managing the relationships between users and mines within the system.
     *
     * @return an instance of {@link IMineUserStore} for mine-user association operations
     */
    @NotNull IMineUserStore mineUserStore();

}
