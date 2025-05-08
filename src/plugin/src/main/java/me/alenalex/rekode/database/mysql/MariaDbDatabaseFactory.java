package me.alenalex.rekode.database.mysql;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import me.alenalex.rekode.abstractions.database.IDatabaseFactory;
import me.alenalex.rekode.abstractions.database.IDatabaseProvider;
import me.alenalex.rekode.abstractions.exceptions.FailedDbFactoryException;
import me.alenalex.rekode.base.contracts.config.IDatabaseContract;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class MariaDbDatabaseFactory implements IDatabaseFactory {

    @Override
    public @NotNull IDatabaseProvider create(IDatabaseContract contract) throws FailedDbFactoryException {
        try {
            final HikariConfig config = new HikariConfig();
            
            // Set basic connection properties
            config.setDriverClassName("org.mariadb.jdbc.Driver");
            config.setJdbcUrl(String.format("jdbc:mariadb://%s:%d/%s", 
                    contract.host(), 
                    contract.port(), 
                    contract.database()));
            config.setUsername(contract.username());
            config.setPassword(contract.password());
            
            // Configure connection pool
            config.setPoolName("Rekode-MariaDB-Pool");
            config.setMinimumIdle(5);
            config.setIdleTimeout(300000); // 5 minutes
            config.setMaxLifetime(600000); // 10 minutes
            config.setConnectionTimeout(10000); // 10 seconds
            
            // Set default MariaDB connection properties for better performance and stability
            config.addDataSourceProperty("cachePrepStmts", "true");
            config.addDataSourceProperty("prepStmtCacheSize", "250");
            config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
            config.addDataSourceProperty("useServerPrepStmts", "true");
            config.addDataSourceProperty("useLocalSessionState", "true");
            config.addDataSourceProperty("rewriteBatchedStatements", "true");
            config.addDataSourceProperty("cacheResultSetMetadata", "true");
            config.addDataSourceProperty("cacheServerConfiguration", "true");
            config.addDataSourceProperty("elideSetAutoCommits", "true");
            config.addDataSourceProperty("maintainTimeStats", "false");
            
            // Add any additional user-defined properties
            for (Map.Entry<String, String> entry : contract.properties().entrySet()) {
                config.addDataSourceProperty(entry.getKey(), entry.getValue());
            }
            
            // Create the data source
            HikariDataSource dataSource = new HikariDataSource(config);
            
            // Return the database provider
            return new MariaDbDatabaseProvider(dataSource);
        } catch (Exception e) {
            throw new FailedDbFactoryException("Failed to create MariaDB database provider", e);
        }
    }
}
