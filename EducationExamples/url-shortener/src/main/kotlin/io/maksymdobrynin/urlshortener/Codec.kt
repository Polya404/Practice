package io.maksymdobrynin.urlshortener

import java.net.URI

interface Codec {
	/**
	 * This method encodes a given url in a way it will be shortened with accordance to
	 * chosen algorithm. Please see implementation details to know more about an algorithm.
	 *
	 * @param origin long url to be shortened
	 * @return a shortened url with accordance to implemented algorithm
	 */
	suspend fun encode(origin: URI): URI

	/**
	 * This method decodes a given short url to original one with accordance to
	 * chosen algorithm. Please see implementation details to know more about an algorithm.
	 *
	 * @param shortened short url to be decoded to original one
	 * @return an original url
	 */
	suspend fun decode(shortened: URI): URI?
}
