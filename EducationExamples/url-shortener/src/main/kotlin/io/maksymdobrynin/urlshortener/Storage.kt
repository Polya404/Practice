package io.maksymdobrynin.urlshortener

interface Storage<K, V> {
	fun put(
		key: K,
		value: V,
	)

	fun get(key: K): V?
}
