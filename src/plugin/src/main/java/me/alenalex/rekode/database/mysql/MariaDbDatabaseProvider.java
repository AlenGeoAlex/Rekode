package me.alenalex.rekode.database.mysql;

import com.zaxxer.hikari.HikariDataSource;
import me.alenalex.rekode.abstractions.database.IDatabaseProvider;
import me.alenalex.rekode.database.AbstractDatabaseProvider;
import org.jooq.SQLDialect;

import java.sql.Connection;
import java.sql.SQLException;

public class MariaDbDatabaseProvider extends AbstractDatabaseProvider {

    public MariaDbDatabaseProvider(HikariDataSource dataSource) {
        super(dataSource);
    }

    @Override
    public SQLDialect dialect() {
        return SQLDialect.MARIADB;
    }

    @Override
    public Connection createConnection() throws SQLException {
        return dataSource.getConnection();
    }
}
