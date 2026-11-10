package io.maksymdobrynin.urlshortener

import java.net.URI

/**
 * An in-memory implementation of the [Storage] interface that maps string keys to URI pairs.
 *
 * This class utilizes a [MutableMap] to store key-value pairs, where each key is a string
 * (typically a hash) and the value is a [Pair] of URIs representing the original and shortened URLs.
 *
 * **Note:** The effectiveness of this storage mechanism is contingent upon the quality of the
 * hash function used to generate the keys. A poor hash function may lead to collisions,
 * resulting in overwriting existing entries. It's crucial to ensure that the hash function
 * provides a uniform distribution to minimize such risks.
 *
 * @constructor Initializes the storage with an optional pre-existing [MutableMap].
 * @property storage The underlying map that holds the stored key-value pairs.
 */
class HashStorage(
	private val storage: MutableMap<String, Pair<URI, URI>> = mutableMapOf(),
) : Storage<String, Pair<URI, URI>> {
	/**
	 * Associates the specified [value] with the given [key] in the storage.
	 * If the storage previously contained a mapping for the key, the old value is replaced.
	 *
	 * @param key The key with which the specified value is to be associated.
	 * @param value The value to be associated with the specified key.
	 */
	override fun put(
		key: String,
		value: Pair<URI, URI>,
	) {
		storage[key] = value
	}

	/**
	 * Retrieves the value associated with the specified [key], or `null` if no such mapping exists.
	 *
	 * @param key The key whose associated value is to be returned.
	 * @return The value associated with the specified key, or `null` if no mapping exists.
	 */
	override fun get(key: String): Pair<URI, URI>? = storage[key]
}
