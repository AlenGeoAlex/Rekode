package me.alenalex.rekode.store;

import me.alenalex.rekode.RekodePlugin;
import me.alenalex.rekode.abstractions.store.IUserStore;
import me.alenalex.rekode.gen.entities.tables.daos.UsersDao;
import me.alenalex.rekode.gen.entities.tables.pojos.Users;
import org.jetbrains.annotations.NotNull;
import org.jooq.Configuration;
import org.jooq.Function0;
import org.jooq.impl.DefaultConfiguration;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class UserStore implements IUserStore {

    private final RekodePlugin plugin;

    public UserStore(RekodePlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public @NotNull CompletableFuture<Optional<Users>> getUserByIdAsync(@NotNull UUID userId) {
        return CompletableFuture.supplyAsync(new Function0<>() {
            @Override
            public Optional<Users> apply() {
                try (Connection connection = plugin.dbProvider().createConnection()) {
                    Configuration jooqConfiguration = new DefaultConfiguration()
                            .set(connection)
                            .set(plugin.dbProvider().dialect());

                    UsersDao usersDao = new UsersDao(jooqConfiguration);

                    Users userPojo = usersDao.findById(userId);
                    return Optional.ofNullable(userPojo);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    @Override
    public @NotNull CompletableFuture<Boolean> createUserAsync(@NotNull Users user) {
        return CompletableFuture.supplyAsync(new Function0<Boolean>() {
            @Override
            public Boolean apply() {
                try (Connection connection = plugin.dbProvider().createConnection()) {
                    Configuration jooqConfiguration = new DefaultConfiguration()
                            .set(connection)
                            .set(plugin.dbProvider().dialect());

                    UsersDao usersDao = new UsersDao(jooqConfiguration);

                    usersDao.insert(user);
                    return true;
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    @Override
    public @NotNull CompletableFuture<Boolean> updateUserAsync(@NotNull Users user) {
        return CompletableFuture.supplyAsync(new Function0<Boolean>() {
            @Override
            public Boolean apply() {
                try (Connection connection = plugin.dbProvider().createConnection()) {
                    Configuration jooqConfiguration = new DefaultConfiguration()
                            .set(connection)
                            .set(plugin.dbProvider().dialect());

                    UsersDao usersDao = new UsersDao(jooqConfiguration);

                    usersDao.update(user);
                    return true;
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    @Override
    public @NotNull CompletableFuture<Boolean> deleteUserAsync(@NotNull UUID userId) {
        return CompletableFuture.supplyAsync(new Function0<Boolean>() {
            @Override
            public Boolean apply() {
                try (Connection connection = plugin.dbProvider().createConnection()) {
                    Configuration jooqConfiguration = new DefaultConfiguration()
                            .set(connection)
                            .set(plugin.dbProvider().dialect());

                    UsersDao usersDao = new UsersDao(jooqConfiguration);

                    usersDao.deleteById(userId);
                    return true;
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

}
