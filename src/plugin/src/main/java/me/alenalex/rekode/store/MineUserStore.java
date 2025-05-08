package me.alenalex.rekode.store;

import me.alenalex.rekode.RekodePlugin;
import me.alenalex.rekode.abstractions.models.IRekodeMineUser;
import me.alenalex.rekode.abstractions.store.IMineUserStore;
import me.alenalex.rekode.gen.entities.Tables;
import me.alenalex.rekode.gen.entities.tables.daos.MineUsersDao;
import me.alenalex.rekode.gen.entities.tables.pojos.MineUsers;
import org.jetbrains.annotations.NotNull;
import org.jooq.Configuration;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;
import org.jooq.impl.DefaultConfiguration;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class MineUserStore implements IMineUserStore {

    private final RekodePlugin plugin;

    public MineUserStore(RekodePlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public @NotNull CompletableFuture<Optional<MineUsers>> getOwnedMineOfUserAsync(@NotNull UUID userId) {
        Objects.requireNonNull(userId, "userId cannot be null");
        return CompletableFuture.supplyAsync(() -> {
            try (Connection connection = plugin.dbProvider().createConnection()) {
                Configuration jooqConfiguration = new DefaultConfiguration()
                        .set(connection)
                        .set(plugin.dbProvider().dialect());

                MineUsersDao mineUsersDao = new MineUsersDao(jooqConfiguration);
                return mineUsersDao.fetchOptionalByMineUserId(userId);
            }catch (SQLException e){
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public @NotNull CompletableFuture<List<MineUsers>> getMembersOfMineAsync(@NotNull UUID mineId) {
        Objects.requireNonNull(mineId, "mineId cannot be null");
        return CompletableFuture.supplyAsync(() -> {
            try (Connection connection = plugin.dbProvider().createConnection()) {
                DSLContext context = DSL.using(connection, plugin.dbProvider().dialect());

                return context.select()
                        .from(Tables.MINE_USERS)
                        .where(
                                Tables.MINE_USERS.MINE_ID.eq(mineId) // The mineId
                                        .and(Tables.MINE_USERS.ROLE.ne(IRekodeMineUser.Roles.owner())) // and the role should not be owner
                        ).fetch()
                        .into(MineUsers.class);
            }catch (SQLException e){
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public @NotNull CompletableFuture<Boolean> createMineUserAsync(@NotNull MineUsers mineUser) {
        Objects.requireNonNull(mineUser, "mineUser cannot be null");
        return CompletableFuture.supplyAsync(() -> {
            try (Connection connection = plugin.dbProvider().createConnection()) {
                Configuration jooqConfiguration = new DefaultConfiguration()
                        .set(connection)
                        .set(plugin.dbProvider().dialect());

                MineUsersDao mineUsersDao = new MineUsersDao(jooqConfiguration);
                mineUsersDao.insert(mineUser);
                return true;
            }catch (SQLException e){
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public @NotNull CompletableFuture<Boolean> updateMineUserAsync(@NotNull MineUsers mineUser) {
        Objects.requireNonNull(mineUser, "mineUser cannot be null");
        return CompletableFuture.supplyAsync(() -> {
            try (Connection connection = plugin.dbProvider().createConnection()) {
                Configuration jooqConfiguration = new DefaultConfiguration()
                        .set(connection)
                        .set(plugin.dbProvider().dialect());

                MineUsersDao mineUsersDao = new MineUsersDao(jooqConfiguration);
                mineUsersDao.update(mineUser);
                return true;
            }catch (SQLException e){
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public @NotNull CompletableFuture<Boolean> deleteMineUserAsync(@NotNull UUID mineUserId) {
        Objects.requireNonNull(mineUserId, "mineUserId cannot be null");
        return CompletableFuture.supplyAsync(() -> {
            try (Connection connection = plugin.dbProvider().createConnection()) {
                Configuration jooqConfiguration = new DefaultConfiguration()
                        .set(connection)
                        .set(plugin.dbProvider().dialect());

                MineUsersDao mineUsersDao = new MineUsersDao(jooqConfiguration);
                mineUsersDao.deleteById(mineUserId);
                return true;
            }catch (SQLException e){
                throw new RuntimeException(e);
            }
        });
    }
}
