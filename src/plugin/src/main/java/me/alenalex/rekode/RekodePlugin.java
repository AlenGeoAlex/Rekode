package me.alenalex.rekode;

import me.alenalex.rekode.abstractions.IRekode;
import me.alenalex.rekode.abstractions.IRekodeServices;
import me.alenalex.rekode.abstractions.IRekodeStores;
import me.alenalex.rekode.abstractions.database.IDatabaseFactory;
import me.alenalex.rekode.abstractions.database.IDatabaseProvider;
import me.alenalex.rekode.database.AbstractDatabaseProvider;
import me.alenalex.rekode.database.mysql.MariaDbDatabaseFactory;
import me.alenalex.rekode.logger.RekodeLoggerFactory;
import org.bukkit.Bukkit;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;

public class RekodePlugin extends JavaPlugin {

    public static RekodePlugin INSTANCE = null;
    private IRekode rekodeInstance = null;
    private final RekodeConfiguration configuration = new RekodeConfiguration();
    private AbstractDatabaseProvider dbProvider = null;
    private Logger rekodeLogger;
    private final IRekodeStores stores = new RekodeStores(this);
    private final IRekodeServices services = new RekodeServices(this);

    @Override
    public void onEnable() {
        super.onEnable();
        EnsureSingleton();
        INSTANCE = this;
        this.rekodeInstance = new Rekode();
        this.rekodeLogger = this.rekodeInstance.loggerFactory().getLogger(this.getClass().getName());

        // Read the configuration
        if (!configuration.initialize()) {
            this.getLogger().warning("Failed to initialize configuration. Disabling plugin.");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }

        // Only use the shutdown method from below
        RekodeLoggerFactory.initialize(configuration.logger(), getLogger(), this.getClass().getName());

        // Initialize the dbFactory and create a dbProvider
        if (!connectToDatabase()) {
            this.shutdown("Failed to connect to database.");
            return;
        }

        if(!this.initializeServices()){
            this.shutdown("Failed to initialize services.");
            return;
        }


        provideApi();
    }

    @Override
    public void onDisable() {
        super.onDisable();

        this.stopServices();

        if(dbProvider != null){
            dbProvider.disconnect();
            this.rekodeLogger.info("Disconnected from database.");
        }
    }



    /**
     * Ensures that the RekodePlugin instance is a singleton.
     */
    private static void EnsureSingleton() {
        if (INSTANCE != null)
            throw new IllegalStateException("RekodePlugin is already initialized. This plugin does not support server reloads.");
    }

    /**
     * Registers the Rekode API to the Bukkit Services Manager,
     * enabling external plugins to access the IRekode interface implementation.
     *
     * This method ensures that the `IRekode` instance is exposed as a service
     * with a `ServicePriority.Normal` priority level. Additionally, this method
     * logs a confirmation message via the plugin's logger once the registration is
     * successfully completed.
     */
    private void provideApi(){
        Bukkit.getServicesManager().register(IRekode.class, rekodeInstance, this, ServicePriority.Normal);
        getLogger().info("Rekode API Registered.");
    }

    private boolean connectToDatabase(){
        try {
            IDatabaseFactory factory;
            switch (configuration.database().type()){
                default -> {
                    factory = new MariaDbDatabaseFactory();
                    break;
                }
            }

            this.dbProvider = (AbstractDatabaseProvider) factory.create(configuration.database());
            return true;
        }catch (Exception e){
            this.rekodeLogger.error("Failed to connect to database due to an exception."+ e.getMessage());
            return false;
        }
    }

    private boolean initializeServices(){
        try {
            RekodeServices rekodeServices = (RekodeServices) this.services;
            return rekodeServices.initialize();
        }catch (Exception e){
            return false;
        }
    }

    private void stopServices(){
        try {
            RekodeServices rekodeServices = (RekodeServices) this.services;
            rekodeServices.stop();
        }catch (Exception e){
            this.rekodeLogger.error("Failed to stop services due to an exception."+ e.getMessage());
        }
    }

    /**
     * Safely shuts down the Rekode plugin, optionally logging a specified reason before deactivating the plugin.
     * If a reason is provided, it will be logged as a warning. A general warning message indicating that the
     * plugin has been disabled is always logged.
     *
     * @param reason a String providing the reason for the shutdown, or null if no specific reason is given
     */
    private void shutdown(@Nullable String reason){
        if(reason != null)
            this.rekodeLogger.warn(reason);
        this.rekodeLogger.warn("Rekode Plugin Disabled.");

        Bukkit.getPluginManager().disablePlugin(this);
    }

    public IRekode getRekodeInstance() {
        return rekodeInstance;
    }

    public RekodeConfiguration configuration() {
        return configuration;
    }

    public IDatabaseProvider dbProvider() {
        return dbProvider;
    }

    public IRekodeStores stores() {
        return stores;
    }

    public IRekodeServices services() {
        return services;
    }
}
