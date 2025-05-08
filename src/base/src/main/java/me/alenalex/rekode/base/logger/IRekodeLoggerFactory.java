package me.alenalex.rekode.base.logger;

public interface IRekodeLoggerFactory {

    org.slf4j.Logger getLogger(Class<?> clazz);

    org.slf4j.Logger getLogger(String name);

}
