package com.jetbulb.simple.cache.with.eviciton.policy;

import java.util.Optional;

/**
 * Represents a cache that may store a value under a key.
 * Both key and value may be kind of any type.
 * <p>
 * A way of the storing objects depends on implementation.
 *
 * @param <K> key to store a value (object) under
 * @param <V> value associated with a key
 */
public interface Cache<K, V> {

    /**
     * Puts a given object associated with a given key into this cache.
     *
     * @param key to store a value (object) under
     * @param obj associated with a key
     * @return true if a given object put successfully, otherwise false
     */
    boolean put(K key, V obj);

    /**
     * Retrieves an object associated with a given key of this cache.
     *
     * @param key under what an associated value stored in this cache
     * @return Nullable {@link Optional} that may whether not contain an object associated with a given key
     */
    Optional<V> get(K key);

    /**
     * Removes an object associated with a given key of this cache.
     *
     * @param key under what an associated value stored in this cache
     * @return true if an object found associated with a given key, otherwise false
     */
    boolean remove(K key);
}
