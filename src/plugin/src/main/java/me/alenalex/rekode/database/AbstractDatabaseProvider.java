package me.alenalex.rekode.database;

import com.zaxxer.hikari.HikariDataSource;
import me.alenalex.rekode.abstractions.database.IDatabaseProvider;

public abstract class AbstractDatabaseProvider implements IDatabaseProvider {

    protected final HikariDataSource dataSource;

    public AbstractDatabaseProvider(HikariDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void disconnect() {
        if (dataSource == null) return;
        if(dataSource.isClosed()) return;

        dataSource.close();
    }
}
