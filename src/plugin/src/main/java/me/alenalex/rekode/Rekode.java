package me.alenalex.rekode;

import me.alenalex.rekode.abstractions.IRekode;
import me.alenalex.rekode.abstractions.IRekodeConfiguration;
import me.alenalex.rekode.abstractions.IRekodeServices;
import me.alenalex.rekode.abstractions.IRekodeStores;
import me.alenalex.rekode.abstractions.database.IDatabaseFactory;
import me.alenalex.rekode.base.IRekodeBase;
import me.alenalex.rekode.base.logger.IRekodeLoggerFactory;
import me.alenalex.rekode.entities.IRekodeEntities;
import me.alenalex.rekode.logger.RekodeLoggerFactory;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public class Rekode implements IRekode {

    @Override
    public @NotNull JavaPlugin plugin() {
        return RekodePlugin.INSTANCE;
    }

    @Override
    public @NotNull IRekodeEntities entities() {
        return IRekodeEntities.get();
    }

    @Override
    public @NotNull IRekodeBase base() {
        return IRekodeBase.get();
    }

    @Override
    public @NotNull IRekodeConfiguration configuration() { return RekodePlugin.INSTANCE.configuration(); }

    @Override
    public @NotNull IRekodeLoggerFactory loggerFactory() {
        return RekodeLoggerFactory.instance;
    }

    @Override
    public @NotNull IDatabaseFactory databaseFactory() {
        return (IDatabaseFactory) RekodePlugin.INSTANCE.dbProvider();
    }

    @Override
    public @NotNull IRekodeStores stores() {
        return RekodePlugin.INSTANCE.stores();
    }

    @Override
    public @NotNull IRekodeServices services() {
        return RekodePlugin.INSTANCE.services();
    }


}
