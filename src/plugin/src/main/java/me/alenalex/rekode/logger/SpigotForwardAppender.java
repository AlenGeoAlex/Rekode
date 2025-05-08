package me.alenalex.rekode.logger;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.AppenderBase;
import ch.qos.logback.core.encoder.Encoder;

import java.util.logging.Level;
import java.util.logging.Logger;

public class SpigotForwardAppender extends AppenderBase<ILoggingEvent> {

    private Logger spigotPluginLogger;
    private Encoder<ILoggingEvent> encoder;

    public SpigotForwardAppender() {}

    public void setSpigotPluginLogger(Logger spigotPluginLogger) {
        this.spigotPluginLogger = spigotPluginLogger;
    }

    public void setEncoder(Encoder<ILoggingEvent> encoder) {
        this.encoder = encoder;
    }


    @Override
    protected void append(ILoggingEvent eventObject) {
        if (spigotPluginLogger == null || !isStarted() || encoder == null) {
            return;
        }

        String formattedMessage = new String(encoder.encode(eventObject));
        Level julLevel;
        ch.qos.logback.classic.Level logbackLevel = eventObject.getLevel();

        if (logbackLevel.isGreaterOrEqual(ch.qos.logback.classic.Level.ERROR)) {
            julLevel = Level.SEVERE;
        } else if (logbackLevel.isGreaterOrEqual(ch.qos.logback.classic.Level.WARN)) {
            julLevel = Level.WARNING;
        } else if (logbackLevel.isGreaterOrEqual(ch.qos.logback.classic.Level.INFO)) {
            julLevel = Level.INFO;
        } else if (logbackLevel.isGreaterOrEqual(ch.qos.logback.classic.Level.DEBUG)) {
            julLevel = Level.FINE;
        } else if (logbackLevel.isGreaterOrEqual(ch.qos.logback.classic.Level.TRACE)) {
            julLevel = Level.FINER;
        } else {
            julLevel = Level.CONFIG;
        }
        spigotPluginLogger.log(julLevel, formattedMessage.trim());
    }

    @Override
    public void start() {
        if (this.encoder == null) {
            addError("No encoder set for appender [" + name + "].");
            return;
        }
        if (this.spigotPluginLogger == null) {
            addError("No Spigot Plugin Logger set for appender [" + name + "].");
            return;
        }
        encoder.start();
        super.start();
    }

    @Override
    public void stop() {
        if (encoder != null) {
            encoder.stop();
        }
        super.stop();
    }
}
