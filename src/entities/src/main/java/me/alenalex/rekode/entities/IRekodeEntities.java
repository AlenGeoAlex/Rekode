package me.alenalex.rekode.entities;

public interface IRekodeEntities {

    /**
     * Provides the singleton instance of the {@link IRekodeEntities} implementation.
     * @return the singleton instance of {@link IRekodeEntities}
     */
    static IRekodeEntities get() {
        return RekodeEntities.INSTANCE;
    }

}
