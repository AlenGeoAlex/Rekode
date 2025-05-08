package me.alenalex.rekode.contracts.config;

import de.exlll.configlib.Comment;
import de.exlll.configlib.Configuration;
import me.alenalex.rekode.RekodePlugin;
import me.alenalex.rekode.base.contracts.config.ILoggerContract;

import java.nio.file.Path;

// Fields unused warnings are there, that is not needed
@SuppressWarnings("all")
@Configuration
public class LoggerContract implements ILoggerContract {

    public LoggerContract() {
    }

    @Comment({"Whether you wish to enable higher level of tracing and include additional message", "Default: false"})
    private final boolean debugEnabled = false;

    @Comment({"Whether you wish to write the log to a file", "Default: false"})
    private final boolean writeToFile = false;

    @Override
    public boolean isDebugEnabled() {
        return this.debugEnabled;
    }

    @Override
    public boolean writeToFile() {
        return this.writeToFile;
    }

    @Override
    public Path getLogFilePath() {
        return RekodePlugin.INSTANCE.getDataFolder().toPath().resolve("logs");
    }
}
