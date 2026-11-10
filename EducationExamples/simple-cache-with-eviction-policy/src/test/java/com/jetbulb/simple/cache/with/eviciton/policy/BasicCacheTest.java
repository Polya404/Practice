package com.jetbulb.simple.cache.with.eviciton.policy;

import org.assertj.core.api.SoftAssertions;
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SoftAssertionsExtension.class)
class BasicCacheTest {

    @Test
    void shouldPutNewObjectIntoTheCache() {
        var cache = new BasicCache<>();
        assertThat(cache.put("1", "Hello")).isTrue();
    }

    @Test
    void shouldReplaceExistingObjectIntoTheCache(SoftAssertions softly) {
        var cache = new BasicCache<>();
        softly.assertThat(cache.put("1", "Hello")).isTrue();
        softly.assertThat(cache.put("1", "Hello2")).isTrue();
    }

    @Test
    void shouldRetrieveObjectFromTheCacheSinceObjectAssociatedWithGivenKeyFound() {
        var cache = new BasicCache<>();
        cache.put("1", "Hello");
        assertThat(cache.get("1")).hasValue("Hello");
    }

    @Test
    void shouldNotRetrieveAnyObjectFromTheCacheSinceNoObjectAssociatedWithGivenKeyFound() {
        var cache = new BasicCache<>();
        assertThat(cache.get("1")).isEmpty();
    }

    @Test
    void shouldRemoveObjectFromTheCacheSinceObjectAssociatedWithGivenKeyFound() {
        var cache = new BasicCache<>();
        cache.put("1", "Hello");
        assertThat(cache.remove("1")).isTrue();
    }

    @Test
    void shouldNotRemoveAnyObjectFromTheCacheSinceNoObjectAssociatedWithGivenKeyFound() {
        var cache = new BasicCache<>();
        assertThat(cache.remove("1")).isFalse();
    }
}