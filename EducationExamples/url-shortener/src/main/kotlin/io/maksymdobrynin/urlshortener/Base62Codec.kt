package io.maksymdobrynin.urlshortener

import java.net.URI
import java.util.function.Supplier

/**
 * This code implements Base 62 Conversion algorithm to prepare a shortened url based on given one.
 */
class Base62Codec(
	private val scheme: String,
	private val host: String,
	private val idValueGenerator: Supplier<Long>,
	private val storage: Storage<String, Pair<URI, URI>> = HashStorage(),
) : Codec {
	companion object {
		private const val MAX_SHORT_URL_PATH_LENGTH = 7
		private const val BASE62 = 62
		private val BASE62_ALPHABET: CharArray =
			charArrayOf(
				'0',
				'1',
				'2',
				'3',
				'4',
				'5',
				'6',
				'7',
				'8',
				'9',
				'a',
				'b',
				'c',
				'd',
				'e',
				'f',
				'g',
				'h',
				'i',
				'j',
				'k',
				'l',
				'm',
				'n',
				'o',
				'p',
				'q',
				'r',
				's',
				't',
				'u',
				'v',
				'w',
				'x',
				'y',
				'z',
				'A',
				'B',
				'C',
				'D',
				'E',
				'F',
				'G',
				'H',
				'I',
				'J',
				'K',
				'L',
				'M',
				'N',
				'O',
				'P',
				'Q',
				'R',
				'S',
				'T',
				'U',
				'V',
				'W',
				'X',
				'Y',
				'Z',
			)
	}

	override suspend fun encode(origin: URI): URI {
		val hashValue = hashValue()
		val tinyUrl = URI(scheme, host, "/$hashValue", null)

		storage.put(hashValue, Pair(origin, tinyUrl))

		return tinyUrl
	}

	override suspend fun decode(shortened: URI): URI? = storage.get(shortened.path.replace("/", ""))?.first

	private fun hashValue(): String =
		buildString {
			var id = idValueGenerator.get()
			while (id > 0 && length < MAX_SHORT_URL_PATH_LENGTH) {
				val reminder = (id % BASE62).toInt()
				append(BASE62_ALPHABET[reminder])
				id /= BASE62
			}
		}
}
