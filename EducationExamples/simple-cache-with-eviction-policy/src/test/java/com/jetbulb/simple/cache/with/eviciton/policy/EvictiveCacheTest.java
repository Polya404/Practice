package com.jetbulb.simple.cache.with.eviciton.policy;

import org.assertj.core.api.SoftAssertions;
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.LinkedList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(SoftAssertionsExtension.class)
class EvictiveCacheTest {

    @Test
    void shouldPutNewObjectIntoTheCache() {
        var cache = new EvictiveCache<>();
        assertThat(cache.put("1", "Hello")).isTrue();
    }

    @Test
    void shouldReplaceExistingObjectIntoTheCache(SoftAssertions softly) {
        var cache = new EvictiveCache<>();
        softly.assertThat(cache.put("1", "Hello")).isTrue();
        softly.assertThat(cache.put("1", "Hello2")).isTrue();
    }

    @Test
    void shouldOverrideExistingObjectWhenEqualKeyFound() {
        var spyInternalStorage = spy(new LinkedList<CacheItem<String, String>>());
        var cache = new EvictiveCache<>(spyInternalStorage, 5);

        cache.put("1", "Hello");
        cache.put("1", "Hello");
        cache.put("1", "Hello2");

        assertThat(spyInternalStorage)
                .doesNotHaveDuplicates()
                .hasSize(1);
    }

    @Test
    void shouldExcludeObjectWithEqualsKeysWhileInitializingCacheViaTestingConstructor() {
        var spyInternalStorage = spy(new LinkedList<CacheItem<String, String>>());
        var cache = new EvictiveCache<>(
                spyInternalStorage,
                5,
                new CacheItem<>("1", "Hello"),
                new CacheItem<>("1", "Hello"),
                new CacheItem<>("1", "Hello2"),
                new CacheItem<>("2", "Hello2")
        );

        assertThat(spyInternalStorage)
                .doesNotHaveDuplicates()
                .hasSize(2);
    }

    @Test
    void shouldRetrieveObjectFromTheCacheSinceObjectAssociatedWithGivenKeyFound() {
        var spyInternalStorage = spy(new LinkedList<CacheItem<String, String>>());
        var cache = new EvictiveCache<>(spyInternalStorage, 5);

        cache.put("1", "Hello");
        verify(spyInternalStorage, times(1)).offer(any(CacheItem.class));
    }

    @Test
    void shouldRetrieveObjectFromTheCacheSinceObjectAssociatedWithGivenKeyFoundAndApplyEvictionPolicy() {
        var spyInternalStorage = spy(new LinkedList<CacheItem<String, String>>());
        var cache = new EvictiveCache<>(spyInternalStorage, 1, new CacheItem<>("1", "Hello"));
        cache.put("2", ",");

        verify(spyInternalStorage, times(1)).poll();
        verify(spyInternalStorage, times(1)).offer(any(CacheItem.class));
    }

    @Test
    void shouldRetrieveObjectFromTheCacheSinceObjectAssociatedWithGivenKeyFoundAndMakeItAsTheMostRecent() {
        var spyInternalStorage = spy(new LinkedList<CacheItem<String, String>>());
        var cache = new EvictiveCache<>(
                spyInternalStorage,
                5,
                new CacheItem<>("1", "Hello"),
                new CacheItem<>("2", ","),
                new CacheItem<>("3", " "),
                new CacheItem<>("4", "World")
        );

        assertThat(cache.get("1")).hasValue("Hello");
        verify(spyInternalStorage, times(1)).offer(any(CacheItem.class));

    }

    @Test
    void shouldNotRetrieveAnyObjectFromTheCacheSinceNoObjectAssociatedWithGivenKeyFound() {
        var cache = new EvictiveCache<>();
        assertThat(cache.get("1")).isEmpty();
    }

    @Test
    void shouldRemoveObjectFromTheCacheSinceObjectAssociatedWithGivenKeyFound() {
        var cache = new EvictiveCache<>(new LinkedList<>(), 5, new CacheItem<>("1", "Hello"));
        assertThat(cache.remove("1")).isTrue();
    }

    @Test
    void shouldNotRemoveAnyObjectFromTheCacheSinceNoObjectAssociatedWithGivenKeyFound() {
        var cache = new EvictiveCache<>();
        assertThat(cache.remove("1")).isFalse();
    }
}