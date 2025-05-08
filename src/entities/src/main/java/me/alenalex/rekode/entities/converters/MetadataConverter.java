package me.alenalex.rekode.entities.converters;

import com.google.gson.reflect.TypeToken;
import me.alenalex.rekode.base.IRekodeBase;
import me.alenalex.rekode.base.contracts.entities.Metadata;
import org.jetbrains.annotations.NotNull;
import org.jooq.Converter;

import java.lang.reflect.Type;
import java.util.HashMap;

public class MetadataConverter implements Converter<String, Metadata> {

    private static final Type MAP_TYPE_TOKEN = new TypeToken<HashMap<String, String>>() {}.getType();

    public MetadataConverter() {
        // Default constructor required by jOOQ
    }

    @Override
    public Metadata from(String s) {
        if( s == null || s.isEmpty() ){
            s = "{}";
        }

        return new Metadata(IRekodeBase.get().jsonProvider().fromJsonString(s, MAP_TYPE_TOKEN));
    }

    @Override
    public String to(Metadata metadata) {
        return metadata == null ? new Metadata(null).toJson() : metadata.toJson();
    }

    @Override
    public @NotNull Class<String> fromType() {
        return String.class;
    }

    @Override
    public @NotNull Class<Metadata> toType() {
        return Metadata.class;
    }
}
