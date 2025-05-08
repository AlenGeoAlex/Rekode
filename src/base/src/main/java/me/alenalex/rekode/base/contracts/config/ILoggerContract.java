package me.alenalex.rekode.base.contracts.config;

import java.nio.file.Path;

public interface ILoggerContract {

    boolean isDebugEnabled();

    boolean writeToFile();

    Path getLogFilePath();
}
