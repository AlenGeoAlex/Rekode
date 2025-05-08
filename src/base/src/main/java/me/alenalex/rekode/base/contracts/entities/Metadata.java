package me.alenalex.rekode.base.contracts.entities;

import me.alenalex.rekode.base.IRekodeBase;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * The Metadata class provides a thread-safe structure for managing key-value
 * metadata in the form of a ConcurrentHashMap. It also offers capabilities to
 * convert metadata to JSON and perform various operations like adding, removing,
 * clearing, merging, and retrieving metadata elements.
 */
public class Metadata {

    private final ConcurrentHashMap<String, String> metadata;

    public Metadata(@Nullable HashMap<String, String> metadata) {
        if(metadata == null)
            metadata = new HashMap<>();

        this.metadata = new ConcurrentHashMap<>(metadata);
    }

    public String toJson() {
        return IRekodeBase.get().jsonProvider().toJsonString(this.metadata);
    }

    /**
     * Adds or updates a key-value pair in the metadata.
     *
     * @param key the key to be added or updated, must not be null
     * @param value the value to associate with the key
     * @return the current instance of {@code Metadata} after the operation
     * @throws NullPointerException if the key is null
     */
    public Metadata set(String key, String value) {
        Objects.requireNonNull(key, "Key cannot be null.");
        this.metadata.put(key, value);
        return this;
    }

    /**
     * Removes a key-value pair from the metadata using the specified key.
     *
     * @param key the key of the entry to be removed; must not be null
     * @return the current instance of {@code Metadata} after the operation
     * @throws NullPointerException if the key is null
     */
    public Metadata remove(@NotNull String key) {
        Objects.requireNonNull(key, "Key cannot be null.");
        this.metadata.remove(key);
        return this;
    }

    /**
     * Clears all key-value pairs in the metadata and resets it to an empty state.
     *
     * @return the current instance of {@code Metadata} after the operation
     */
    public Metadata clear() {
        this.metadata.clear();
        return this;
    }

    /**
     * Merges the metadata from the provided {@code Metadata} instance into the current instance.
     * Keys and values in the provided metadata will overwrite those in the current instance
     * if they already exist.
     *
     * @param metadata the {@code Metadata} instance whose data is to be merged, must not be null
     * @return the current instance of {@code Metadata} after the merge
     * @throws NullPointerException if the provided {@code Metadata} is null
     */
    public Metadata merge(@NotNull Metadata metadata) {
        this.metadata.putAll(metadata.metadata);
        return this;
    }

    /**
     * Merges the provided metadata into the current instance of {@code Metadata}.
     * Entries in the given map will overwrite existing entries in the current metadata
     * if they have the same keys.
     *
     * @param metadata a map containing key-value pairs to be merged into the current metadata;
     *                 must not be null
     * @return the current instance of {@code Metadata} after the merge
     * @throws NullPointerException if the provided {@code metadata} is null
     */
    public Metadata merge(HashMap<String, String> metadata) {
        this.metadata.putAll(metadata);
        return this;
    }

    /**
     * Removes one or more key-value pairs from the metadata using the specified keys.
     *
     * @param keys the keys of the entries to be removed; must not be null
     * @return the current instance of {@code Metadata} after the operation
     */
    public Metadata remove(String @NotNull ... keys) {
        Objects.requireNonNull(keys, "Key cannot be null.");

        for(String key : keys) {
            this.metadata.remove(key);
        }

        return this;
    }


    /**
     * Retrieves the value associated with the specified key from the metadata.
     * If the key is not present, an empty {@code Optional} is returned.
     *
     * @param key the key whose associated value is to be retrieved; must*/
    public Optional<String> get(String key) {
        Objects.requireNonNull(key, "Key cannot be null.");
        return Optional.ofNullable(this.metadata.get(key));
    }

    /**
     * Retrieves the set of keys present in the metadata.
     *
     * @return a set of strings representing the keys currently in the metadata
     */
    public Set<String> keys() {
        return this.metadata.keySet();
    }

    /**
     * Checks if the metadata is currently empty.
     *
     * @return {@code true} if the metadata is empty, {@code false} otherwise
     */
    public boolean isEmpty() {
        return this.metadata.isEmpty();
    }

    /**
     * Checks if the specified key is present in the metadata.
     *
     * @param key the key to be checked; must not be null
     * @return {@code true} if the key exists in the metadata, {@code false} otherwise
     */
    public boolean containsKey(@NotNull String key) {
        Objects.requireNonNull(key, "Key cannot be null.");
        return this.metadata.containsKey(key);
    }

    /**
     * Inserts the specified value with the specified key into the metadata if the key is not already associated
     * with a value or is mapped to {@code null}. If the key already exists with an associated value, the existing
     * value is returned and no changes are made to the metadata.
     *
     * @param key the key with which the specified value is to be associated, must not be null
     * @param value the value to be associated with the specified key, may be null
     * @return the previous value associated with the specified key, or {@code null} if there was no entry for the key
     *         or if the key was previously associated with {@code null}
     * @throws NullPointerException if the key is null
     */
    public String putIfAbsent(@NotNull String key, String value) {
        Objects.requireNonNull(key, "Key cannot be null.");
        return this.metadata.putIfAbsent(key, value);
    }

    /**
     * Returns the value associated with the given key by computing it using the provided mapping function
     * if the key is not already present in the metadata or is mapped to null. If the key is already present,
     * the existing value is returned without any computation.
     *
     * @param key the key whose associated value is to be computed if absent; must not be null
     * @param mappingFunction the function to compute a value for the given key; must not be null
     * @return the current value associated with the key, or the newly computed value if no value was present
     * @throws NullPointerException if the key or mapping function is null
     */
    public String computeIfAbsent(@NotNull String key, @NotNull java.util.function.Function<? super String, ? extends String> mappingFunction) {
        Objects.requireNonNull(key, "Key cannot be null.");
        Objects.requireNonNull(mappingFunction, "Mapping function cannot be null.");
        return this.metadata.computeIfAbsent(key, mappingFunction);
    }
}
