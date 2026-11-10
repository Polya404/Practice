package com.jetbulb.simple.cache.with.eviciton.policy;

import java.util.LinkedList;
import java.util.Optional;
import java.util.Queue;

/**
 * This class implementing a simple cache that may store a value under a key.
 * Both key and value may be kind of any type.
 * <p>
 * Values will be stored in an internal cache managed by {@link Queue},
 * especially by its implementing {@link LinkedList}.
 *
 * @param <K> key to store a value (object) under
 * @param <V> value associated with a key
 */
public class BasicCache<K, V> implements Cache<K, V> {

    private final Queue<CacheItem<K, V>> cache = new LinkedList<>();

    /**
     * Puts a given object associated with a given key as the tail (last item) of this cache.
     *
     * @param key to store a value (object) under
     * @param obj associated with a key
     * @return always true in no issue occurred while putting a given object
     */
    @Override
    public boolean put(K key, V obj) {
        var it = cache.iterator();

        while (it.hasNext()) {
            CacheItem<K, V> next = it.next();
            if (key.equals(next.key())) {
                it.remove();
                break;
            }
        }

        cache.offer(new CacheItem<>(key, obj));
        return true;
    }

    /**
     * Retrieves a first occurrence of a specified item stored under a given key in this cache.
     * If a cache contains any associated item with a given key, it will retrieve {@link Optional}
     * containing an associated object, otherwise empty {@link Optional}.
     * More formally, retrieves a first object E such that Objects.equals(K1, K2) (if such an object exists).
     *
     * @param key under what an associated value stored in this cache
     * @return Nullable {@link Optional} that may whether not contain an object associated with a given key
     */
    @Override
    public Optional<V> get(K key) {
        return cache.stream()
                .filter(item -> key.equals(item.key()))
                .findFirst()
                .map(CacheItem::value);
    }

    /**
     * Removes a first occurrence of a specified item stored under a given key in this cache.
     * If the cache does not contain any associated item with a given key, it is unchanged.
     * More formally, removes a first object E such that Objects.equals(K1, K2) (if such an object exists).
     *
     * @param key under what an associated value stored in this cache
     * @return true if an object found associated with a given key, otherwise false
     */
    @Override
    public boolean remove(K key) {
        var it = cache.iterator();
        while (it.hasNext()) {
            CacheItem<K, V> next = it.next();
            if (key.equals(next.key())) {
                it.remove();
                return true;
            }
        }
        return false;
    }
}
