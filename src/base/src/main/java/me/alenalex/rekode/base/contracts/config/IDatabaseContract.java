package me.alenalex.rekode.base.contracts.config;

import me.alenalex.rekode.base.enums.RekodeDbType;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public interface IDatabaseContract {

    @NotNull
    RekodeDbType type();

    @NotNull
    String host();

    @NotNull
    String database();

    @NotNull
    String username();

    @NotNull
    String password();

    int port();

    @NotNull
    HashMap<String, String> properties();

}
