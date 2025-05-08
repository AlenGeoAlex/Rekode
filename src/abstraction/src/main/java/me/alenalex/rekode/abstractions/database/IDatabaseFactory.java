package me.alenalex.rekode.abstractions.database;

import me.alenalex.rekode.abstractions.exceptions.FailedDbFactoryException;
import me.alenalex.rekode.base.contracts.config.IDatabaseContract;
import org.jetbrains.annotations.NotNull;

public interface IDatabaseFactory {

    @NotNull
    IDatabaseProvider create(IDatabaseContract contract) throws FailedDbFactoryException;

}
