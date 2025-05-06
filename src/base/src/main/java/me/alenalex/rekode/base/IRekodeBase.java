package me.alenalex.rekode.base;

import me.alenalex.rekode.base.json.IJsonProvider;

public interface IRekodeBase {

    /**
     * Provides the singleton instance of the {@link IRekodeBase} implementation.
     *
     * @return the singleton instance of {@link IRekodeBase}
     */
    static IRekodeBase get(){
        return RekodeBase.INSTANCE;
    }

    /**
     * The JSON Provider
     */
    IJsonProvider jsonProvider();

}
