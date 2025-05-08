package me.alenalex.rekode.abstractions;

import me.alenalex.rekode.base.contracts.config.IDatabaseContract;
import me.alenalex.rekode.base.contracts.config.ILoggerContract;
import org.jetbrains.annotations.NotNull;

public interface IRekodeConfiguration {

    @NotNull
    ILoggerContract logger();

    @NotNull
    IDatabaseContract database();
}
