package me.alenalex.rekode.logger;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.classic.filter.ThresholdFilter;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.rolling.RollingFileAppender;
import ch.qos.logback.core.rolling.TimeBasedRollingPolicy;
import ch.qos.logback.core.util.FileSize;
import me.alenalex.rekode.base.contracts.config.ILoggerContract;
import me.alenalex.rekode.base.logger.IRekodeLoggerFactory;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.Objects;
import java.util.logging.Logger;

public class RekodeLoggerFactory implements IRekodeLoggerFactory {

    public static volatile RekodeLoggerFactory instance; // Singleton instance
    private final LoggerContext loggerContext;
    private final String loggerNamePrefix;

    private RekodeLoggerFactory(ILoggerContract config, Logger logger, String loggerNamePrefix) {
        this.loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
        this.loggerNamePrefix = Objects.requireNonNullElse(loggerNamePrefix, "me.alenalex.rekode");
        configureLogback(config, logger, loggerNamePrefix);
    }

    public static synchronized void initialize(ILoggerContract config, Logger logger, String loggerNamePrefix) {
        if (instance != null)
            return;

        instance = new RekodeLoggerFactory(config, logger, loggerNamePrefix);
    }

    public org.slf4j.Logger getLogger(Class<?> clazz) {
        if (instance == null) {
            System.err.println("FATAL: PluginLoggerFactory not initialized! Returning a basic SLF4J logger for " + clazz.getName() +
                    ". This logger will not use plugin-specific configurations.");
            return LoggerFactory.getLogger(clazz);
        }

        return instance.loggerContext.getLogger(clazz);
    }

    public org.slf4j.Logger getLogger(String name) {
        if (instance == null) {
            System.err.println("FATAL: PluginLoggerFactory not initialized! Returning a basic SLF4J logger for " + name +
                    ". This logger will not use plugin-specific configurations.");
            return LoggerFactory.getLogger(name);
        }

        return instance.loggerContext.getLogger(name);
    }

    private void configureLogback(ILoggerContract contract,
                                  java.util.logging.Logger spigotPluginJulLogger,
                                  String pluginMainPackage) {
        loggerContext.reset();

        Level effectiveConsoleLevel = Level.toLevel("INFO", Level.INFO);
        if (contract.isDebugEnabled() && effectiveConsoleLevel.toInt() > Level.DEBUG_INT) {
            effectiveConsoleLevel = Level.DEBUG;
        }

        Level overallMinLevelForPlugin = effectiveConsoleLevel;

        // --- Spigot Console Forwarding Appender ---
        PatternLayoutEncoder consoleEncoder = new PatternLayoutEncoder();
        consoleEncoder.setContext(loggerContext);
        consoleEncoder.setPattern("%msg%n%ex"); // Spigot adds its own prefix
        consoleEncoder.start();

        SpigotForwardAppender spigotAppender = new SpigotForwardAppender();
        spigotAppender.setContext(loggerContext);
        spigotAppender.setName(pluginMainPackage.replace(".","_") + "_TO_SPIGOT_CONSOLE");
        spigotAppender.setSpigotPluginLogger(spigotPluginJulLogger);
        spigotAppender.setEncoder(consoleEncoder);

        ThresholdFilter consoleFilter = new ThresholdFilter();
        consoleFilter.setContext(loggerContext);
        consoleFilter.setLevel(effectiveConsoleLevel.toString());
        consoleFilter.start();
        spigotAppender.addFilter(consoleFilter);
        spigotAppender.start();

        // --- File Appender (if contract.writeToFile() is true) ---
        RollingFileAppender<ILoggingEvent> fileAppender = null;
        if (contract.writeToFile()) {
            String logDirStr = contract.getLogFilePath().toString();
            String logFileNameBaseStr = "rekode_log.log";

            if (logDirStr.trim().isEmpty()) {
                spigotPluginJulLogger.severe("File logging enabled but log directory or file name base not provided in ILoggerContract. Disabling file logging.");
            } else {
                Level effectiveFileLevel = Level.toLevel("DEBUG", Level.DEBUG);
                if (contract.isDebugEnabled() && effectiveFileLevel.toInt() > Level.DEBUG_INT) {
                    effectiveFileLevel = Level.DEBUG;
                }

                if (effectiveFileLevel.toInt() < overallMinLevelForPlugin.toInt()) {
                    overallMinLevelForPlugin = effectiveFileLevel;
                }

                fileAppender = new RollingFileAppender<>();
                fileAppender.setContext(loggerContext);
                fileAppender.setName(pluginMainPackage.replace(".","_") + "_TO_FILE");

                File logDir = new File(logDirStr);
                if (!logDir.exists() && !logDir.mkdirs()) {
                    spigotPluginJulLogger.severe("Could not create logs directory: " + logDir.getAbsolutePath() + ". File logging will be disabled.");
                    fileAppender = null; // Disable if dir creation fails
                } else {
                    fileAppender.setFile(new File(logDir, logFileNameBaseStr + ".log").getAbsolutePath());

                    PatternLayoutEncoder fileEncoder = new PatternLayoutEncoder();
                    fileEncoder.setContext(loggerContext);
                    fileEncoder.setPattern("[%d{yyyy-MM-dd HH:mm:ss.SSS}] [%thread/%level] [%logger{20}]: %msg%n%ex");
                    fileEncoder.start();
                    fileAppender.setEncoder(fileEncoder);

                    // --- Rolling Policy (Time-based with size cap for daily files, and overall total size cap) ---
                    TimeBasedRollingPolicy<ILoggingEvent> rollingPolicy = new TimeBasedRollingPolicy<>();
                    rollingPolicy.setContext(loggerContext);
                    rollingPolicy.setParent(fileAppender);
                    rollingPolicy.setFileNamePattern(new File(logDir, logFileNameBaseStr + ".%d{yyyy-MM-dd}.%i.log.gz").getAbsolutePath());
                    rollingPolicy.setTotalSizeCap(FileSize.valueOf("10mb")); // Overall cap for all files
                    rollingPolicy.start();
                    fileAppender.setRollingPolicy(rollingPolicy);


                    ThresholdFilter fileOutputFilter = new ThresholdFilter();
                    fileOutputFilter.setContext(loggerContext);
                    fileOutputFilter.setLevel(effectiveFileLevel.toString());
                    fileOutputFilter.start();
                    fileAppender.addFilter(fileOutputFilter);
                    fileAppender.start();
                }
            }
        }
        if (contract.isDebugEnabled() && overallMinLevelForPlugin.toInt() > Level.DEBUG_INT) {
            overallMinLevelForPlugin = Level.DEBUG;
        }

        // Configure the logger for the plugin's main package
        ch.qos.logback.classic.Logger pluginPackageLogger = loggerContext.getLogger(pluginMainPackage);
        pluginPackageLogger.detachAndStopAllAppenders();
        pluginPackageLogger.setAdditive(false); // Crucial
        pluginPackageLogger.addAppender(spigotAppender);
        if (fileAppender != null) {
            pluginPackageLogger.addAppender(fileAppender);
        }
        pluginPackageLogger.setLevel(overallMinLevelForPlugin);

        // Log initialization completion using a logger from the configured system
        org.slf4j.Logger initLogger = getLogger(RekodeLoggerFactory.class.getName() + ".Init");
        initLogger.info("Plugin logging configured for package '{}'. Console Level: {}", pluginMainPackage, effectiveConsoleLevel);
        if (contract.writeToFile() && fileAppender == null) {
            initLogger.warn("File logging was requested but could not be configured (see previous errors).");
        } else {
            initLogger.info("File logging disabled by configuration.");
        }
    }

    public void shutdown() {
        org.slf4j.Logger shutdownLogger = getLogger(RekodeLoggerFactory.class.getName() + ".Shutdown");
        shutdownLogger.info("Shutting down PluginLoggerFactory for '{}'.", this.loggerNamePrefix);
        if (loggerContext != null) {
            loggerContext.stop();
        }
        instance = null;
    }
}