package me.alenalex.rekode.base;

import me.alenalex.rekode.base.json.GsonJsonProvider;
import me.alenalex.rekode.base.json.IJsonProvider;

class RekodeBase implements IRekodeBase {

    public static final RekodeBase INSTANCE = new RekodeBase();

    private static final IJsonProvider JSON_PROVIDER = new GsonJsonProvider();

    @Override
    public IJsonProvider jsonProvider() {
        return JSON_PROVIDER;
    }
}
