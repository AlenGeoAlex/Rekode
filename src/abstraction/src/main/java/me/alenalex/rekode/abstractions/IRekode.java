package me.alenalex.rekode.abstractions;

import me.alenalex.rekode.abstractions.database.IDatabaseFactory;
import me.alenalex.rekode.base.IRekodeBase;
import me.alenalex.rekode.base.logger.IRekodeLoggerFactory;
import me.alenalex.rekode.entities.IRekodeEntities;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public interface IRekode {

    /**
     * Retrieves the JavaPlugin instance associated with this Rekode implementation.
     *
     * @return the JavaPlugin instance
     */
    @NotNull
    JavaPlugin plugin();

    /**
     * Retrieves the RekodeEntities instance associated with this Rekode implementation.
     * @return the RekodeEntities instance
     */
    @NotNull
    IRekodeEntities entities();

    /**
     * Retrieves the RekodeBase instance associated with this Rekode implementation.
     * @return the RekodeBase instance
     */
    @NotNull
    IRekodeBase base();

    /**
     * Provides access to the configuration interface of the Rekode implementation.
     * This interface allows interaction with configuration-related functionalities.
     *
     * @return an instance of {@link IRekodeConfiguration} representing the configuration interface
     */
    @NotNull
    IRekodeConfiguration configuration();

    /**
     * Provides access to the logger factory for creating and managing loggers specific
     * to the Rekode implementation. This interface enables the retrieval of loggers
     * by name or by class to facilitate structured logging in the application.
     *
     * @return an instance of {@link IRekodeLoggerFactory} for retrieving loggers
     */
    @NotNull
    IRekodeLoggerFactory loggerFactory();

    /**
     * Provides access to the factory responsible for creating database connections
     * based on the specified database contract. This method facilitates the dynamic
     * generation and management of database connections as per the configuration.
     *
     * @return an instance of {@link IDatabaseFactory} for creating database providers
     */
    @NotNull
    IDatabaseFactory databaseFactory();

    /**
     * Provides access to the IRekodeStores interface, which allows operations involving
     * multiple store-specific functionalities. This includes managing user-related data,
     * mine-related data, and associations between users and mines.
     *
     * @return an instance of {@link IRekodeStores} for managing store-related functionalities
     */
    @NotNull
    IRekodeStores stores();

    /**
     * Provides access to the IRekodeServices interface, which serves as the entry point
     * for various services available within the Rekode system. These services include
     * user-related and mine-related operations.
     *
     * @return an instance of {@link IRekodeServices} for managing service-related functionalities
     */
    @NotNull
    IRekodeServices services();
}
