package me.alenalex.rekode.store;

import me.alenalex.rekode.RekodePlugin;
import me.alenalex.rekode.abstractions.store.IMineStore;
import me.alenalex.rekode.gen.entities.tables.daos.MinesDao;
import me.alenalex.rekode.gen.entities.tables.pojos.Mines;
import org.jetbrains.annotations.NotNull;
import org.jooq.Configuration;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;
import org.jooq.impl.DefaultConfiguration;

import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.CompletableFuture;

public class MineStore implements IMineStore {

    private final RekodePlugin plugin;

    public MineStore(RekodePlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public @NotNull CompletableFuture<@NotNull Optional<Mines>> getMineByIdAsync(@NotNull UUID mineId) {
        Objects.requireNonNull(mineId, "mineId cannot be null");
        return CompletableFuture.supplyAsync(() -> {
            try(var connection = plugin.dbProvider().createConnection()){
                Configuration jooqConfiguration = new DefaultConfiguration()
                        .set(connection)
                        .set(plugin.dbProvider().dialect());

                MinesDao minesDao = new MinesDao(jooqConfiguration);
                Mines minePojo = minesDao.findById(mineId);
                return Optional.ofNullable(minePojo);
            }catch (SQLException e){
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public @NotNull CompletableFuture<@NotNull Boolean> createMineAsync(@NotNull Mines mine) {
        Objects.requireNonNull(mine, "mine cannot be null");
        return CompletableFuture.supplyAsync(() -> {
            try(var connection = plugin.dbProvider().createConnection()) {
                Configuration jooqConfiguration = new DefaultConfiguration()
                        .set(connection)
                        .set(plugin.dbProvider().dialect());

                MinesDao minesDao = new MinesDao(jooqConfiguration);
                minesDao.insert(mine);
                return true;
            }catch (SQLException e){
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public @NotNull CompletableFuture<@NotNull Boolean> createMinesAsync(@NotNull Iterable<Mines> mines) {
        return CompletableFuture.supplyAsync(() -> {
            try(var connection = plugin.dbProvider().createConnection()) {

                DSLContext context = DSL.using(connection, plugin.dbProvider().dialect());
                context.transaction(configuration -> {
                    try {
                        MinesDao minesDao = new MinesDao(configuration);
                        List<Mines> mineArray = new ArrayList<>();
                        mines.forEach(mineArray::add);
                        minesDao.insert(mineArray);
                    }catch (Exception e){
                        throw new RuntimeException(e);
                    }
                });

                return true;
            }catch (SQLException e){
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public @NotNull CompletableFuture<@NotNull Boolean> updateMineAsync(@NotNull Mines mine) {
        Objects.requireNonNull(mine, "mine cannot be null");
        return CompletableFuture.supplyAsync(() -> {
            try(var connection = plugin.dbProvider().createConnection()) {
                Configuration jooqConfiguration = new DefaultConfiguration()
                        .set(connection)
                        .set(plugin.dbProvider().dialect());

                MinesDao minesDao = new MinesDao(jooqConfiguration);
                minesDao.update(mine);
                return true;
            }catch (SQLException e){
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public @NotNull CompletableFuture<@NotNull Boolean> deleteMineAsync(@NotNull UUID mineId) {
        Objects.requireNonNull(mineId, "mineId cannot be null");
        return CompletableFuture.supplyAsync(() -> {
            try(var connection = plugin.dbProvider().createConnection()) {
                Configuration jooqConfiguration = new DefaultConfiguration()
                        .set(connection)
                        .set(plugin.dbProvider().dialect());

                MinesDao minesDao = new MinesDao(jooqConfiguration);
                minesDao.deleteById(mineId);
                return true;
            }catch (SQLException e){
                throw new RuntimeException(e);
            }
        });
    }
}
