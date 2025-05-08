package me.alenalex.rekode;

import de.exlll.configlib.YamlConfigurationProperties;
import de.exlll.configlib.YamlConfigurationStore;
import me.alenalex.rekode.abstractions.IRekodeConfiguration;
import me.alenalex.rekode.base.contracts.config.IDatabaseContract;
import me.alenalex.rekode.base.contracts.config.ILoggerContract;
import me.alenalex.rekode.contracts.config.DatabaseContract;
import me.alenalex.rekode.contracts.config.LoggerContract;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;
import java.util.logging.Level;

public class RekodeConfiguration implements IRekodeConfiguration {

    private final YamlConfigurationProperties properties = YamlConfigurationProperties.newBuilder()
            .build();

    private final YamlConfigurationStore<LoggerContract> loggerConfigurationStore = new YamlConfigurationStore<>(LoggerContract.class, properties);
    private final YamlConfigurationStore<IDatabaseContract> databaseConfigurationStore = new YamlConfigurationStore<>(IDatabaseContract.class, properties);

    private ILoggerContract loggerContract;
    private IDatabaseContract databaseContract;

    public RekodeConfiguration() {

    }

    public boolean initialize(){
        try {
            this.loggerContract = loggerConfigurationStore.load(ConfigurationPaths.LOGGER);
            if(!ConfigurationPaths.LOGGER.toFile().exists()){
                loggerConfigurationStore.save((LoggerContract) this.loggerContract, ConfigurationPaths.LOGGER);
                RekodePlugin.INSTANCE.getLogger().info("Logger configuration file created.");
            }
        }catch (Exception e){
            RekodePlugin.INSTANCE.getLogger().severe("Failed to load logger configuration file due to an exception."+ e.getMessage());
            RekodePlugin.INSTANCE.getLogger().log(Level.ALL, "Exception: ", e);
            return false;
        }

        try {
            this.databaseContract = databaseConfigurationStore.load(ConfigurationPaths.DATABASE);
            if(!ConfigurationPaths.DATABASE.toFile().exists()){
                databaseConfigurationStore.save((DatabaseContract) this.databaseContract, ConfigurationPaths.DATABASE);
                RekodePlugin.INSTANCE.getLogger().info("Database configuration file created.");
            }
        }catch (Exception e){
            RekodePlugin.INSTANCE.getLogger().severe("Failed to load database configuration file due to an exception."+ e.getMessage());
            RekodePlugin.INSTANCE.getLogger().log(Level.ALL, "Exception: ", e);
            return false;
        }
        return true;
    }

    @NotNull
    @Override
    public ILoggerContract logger() {
        return loggerContract;
    }

    @Override
    public @NotNull IDatabaseContract database() {
        return databaseContract;
    }


    /**
     * A private utility class that encapsulates constant file paths related to the Rekode plugin's configuration.
     * These constants represent various configuration file paths used within the plugin.
     */
    private static final class ConfigurationPaths {
        /**
         * Represents the file path to the logger configuration file for the Rekode plugin.
         * This file is located in the plugin's data folder and is used to store and load
         * logging-related settings specified in the "logger.yml" configuration file.
         */
        public static final Path LOGGER = RekodePlugin.INSTANCE.getDataFolder().toPath().resolve("logger.yml");

        /**
         * Represents the file path to the primary configuration file used by the Rekode plugin.
         * The configuration file is located within the plugin's designated data folder and is named "config.yml".
         * This file is typically used to store various settings and preferences for the plugin.
         */
        public static final Path CONFIG = RekodePlugin.INSTANCE.getDataFolder().toPath().resolve("config.yml");

        /**
         * Represents the file path to the database configuration file used by the Rekode plugin.
         * This file is located*/
        public static final Path DATABASE = RekodePlugin.INSTANCE.getDataFolder().toPath().resolve("database.yml");
    }
}
