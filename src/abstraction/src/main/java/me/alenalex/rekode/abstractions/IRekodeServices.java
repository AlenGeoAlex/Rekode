package me.alenalex.rekode.abstractions;

import me.alenalex.rekode.abstractions.services.IMineService;
import me.alenalex.rekode.abstractions.services.IUserService;
import org.jetbrains.annotations.NotNull;

public interface IRekodeServices {

    /**
     * Provides access to the IMineService interface, which allows interaction with
     * functionalities related to mines within the system.
     *
     * @return an instance of {@link IMineService} for mine-related service operations
     */
    @NotNull IMineService mineService();

    /**
     * Provides access to the {@link IUserService} interface, which serves as the entry point for
     * user-related service operations in the system.
     *
     * @return an instance of {@link IUserService} for managing user-related service operations
     */
    @NotNull IUserService userService();

}
