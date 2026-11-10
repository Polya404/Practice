package com.jetbulb.simple.cache.with.eviciton.policy;

import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Optional;
import java.util.Queue;
import java.util.stream.Collectors;

/**
 * This class implementing a simple cache that may store a value under a key.
 * Both key and value may be kind of any type. Along with a storage purpose,
 * this cache performs Eviction Policy.
 * <p>
 * Eviction Policy:
 * Every {@link Cache#put(Object, Object)} or {@link Cache#get(Object)} method
 * executions will set a given object as the most recent one.
 * If a maximum capacity of objects in this cache exceeded, then the most stale one
 * will be removed and then new one can be put into this cache.
 * <p>
 * Values will be stored in an internal cache managed by {@link Queue},
 * especially by its implementing {@link LinkedList}.
 *
 * @param <K> key to store a value (object) under
 * @param <V> value associated with a key
 */
public class EvictiveCache<K, V> implements Cache<K, V> {

    /**
     * Default maximum number of objects this cache can store
     */
    private static final int MAX_CAPACITY = 32;

    private final Queue<CacheItem<K, V>> cache;
    /**
     * Maximum number of objects this cache can store
     */
    private final int maxCapacity;

    public EvictiveCache() {
        this(MAX_CAPACITY);
    }

    public EvictiveCache(int maxCapacity) {
        this(new LinkedList<>(), maxCapacity);
    }

    /**
     * Testing purpose only.
     *
     * @param cache explicitly set storage of this cache in order to have
     *              a control over it while asserting results
     * @param maxCapacity of a storage in this cache
     * @param objects initially set to this cache (may contain duplicity)
     */
    EvictiveCache(Deque<CacheItem<K, V>> cache, int maxCapacity, CacheItem<K, V>... objects) {
        this.cache = cache;
        this.maxCapacity = maxCapacity;

        var noDuplicates = Arrays.stream(objects)
                .collect(Collectors.groupingBy(CacheItem::key))
                .values()
                .stream()
                .map(l -> l.get(0))
                .toList();

        this.cache.addAll(noDuplicates);
    }

    /**
     * Puts a given object associated with a given key as the tail (last item) of this cache.
     * Putting a given objection to the tail of this queue makes that object the most recent one,
     * hence the most stale one is that what is in the head (first item) of this cache.
     * If {@link #maxCapacity} exceeded, then the most stale object will be removed from this cache.
     *
     * @param key to store a value (object) under
     * @param obj associated with a key
     * @return always true in no issue occurred while putting a given object
     */
    @Override
    public boolean put(K key, V obj) {
        if (cache.size() >= maxCapacity) cache.poll();

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
     * containing an associated object, otherwise empty {@link Optional}, previously moving
     * a found object to the tail of this cache and by this making that object the most recent one.
     * More formally, retrieves a first object E such that Objects.equals(K1, K2) (if such an object exists).
     *
     * @param key under what an associated value stored in this cache
     * @return Nullable {@link Optional} that may whether not contain an object associated with a given key
     */
    @Override
    public Optional<V> get(K key) {
        CacheItem<K, V> oldValue = null;
        var it = cache.iterator();

        while (it.hasNext()) {
            CacheItem<K, V> next = it.next();
            if (key.equals(next.key())) {
                oldValue = next;
                it.remove();
                break;
            }
        }

        if (oldValue == null) return Optional.empty();

        cache.offer(oldValue);
        return Optional.of(oldValue.value());
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

    @Override
    public String toString() {
        return "EvictiveCache{" +
                "size=" + cache.size() +
                ", maxCapacity=" + maxCapacity +
                ", cache=" + cache +
                '}';
    }
}
