package me.alenalex.rekode.abstractions.database;

import org.jooq.SQLDialect;

import java.sql.Connection;
import java.sql.SQLException;

public interface IDatabaseProvider {

    SQLDialect dialect();

    Connection createConnection() throws SQLException;
}
