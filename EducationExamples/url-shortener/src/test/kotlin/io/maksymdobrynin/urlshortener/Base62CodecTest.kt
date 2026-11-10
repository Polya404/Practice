package io.maksymdobrynin.urlshortener

import io.mockk.every
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.verify
import io.mockk.verifyOrder
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.SoftAssertions
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import java.net.URI

/**
 * [See](https://en.wikipedia.org/wiki/Snowflake_ID) more to crack this value.
 */
private const val TWITTER_EPOCH = 1288834974657L

/**
 * Here is expectation that determined [TWITTER_EPOCH] will have to generate this hash value.
 */
private const val HASH_VALUE = "zVcUOGm"
private const val SCHEME = "https"
private const val HOST = "tinyurl.com"
private const val TEST_LONG_URL = "https://maksym.dobrynin.io/url-shortener"

@ExtendWith(SoftAssertionsExtension::class)
class Base62CodecTest {
	@Test
	fun `should encode deterministic short url after encoding with given identifier`() =
		runTest {
			val codec =
				Base62Codec(
					scheme = SCHEME,
					host = HOST,
					idValueGenerator = { TWITTER_EPOCH },
				)
			val actualResult = codec.encode(URI.create(TEST_LONG_URL))
			assertThat(actualResult).isEqualTo(URI.create("https://tinyurl.com/$HASH_VALUE"))
		}

	@Test
	fun `should encode idempotent short url after few encodings (same urls) with same id`(softly: SoftAssertions) =
		runTest {
			val storageSeed = mutableMapOf<String, Pair<URI, URI>>()
			val testStorage = spyk(HashStorage(storageSeed))
			val codec =
				Base62Codec(
					scheme = SCHEME,
					host = HOST,
					idValueGenerator = { TWITTER_EPOCH },
					storage = testStorage,
				)
			val longUrl = URI.create(TEST_LONG_URL)

			val actualResultOne = codec.encode(longUrl)
			val actualResultTwo = codec.encode(longUrl)
			val actualResultThree = codec.encode(longUrl)

			val expectedHashValue = HASH_VALUE
			val expectedResult = URI.create("https://tinyurl.com/$expectedHashValue")

			verify(exactly = 3) {
				testStorage.put(expectedHashValue, longUrl to expectedResult)
			}

			softly.assertThat(actualResultOne).isEqualTo(expectedResult)
			softly.assertThat(actualResultTwo).isEqualTo(expectedResult)
			softly.assertThat(actualResultThree).isEqualTo(expectedResult)
			softly.assertThat(storageSeed).isEqualTo(mapOf(expectedHashValue to (longUrl to expectedResult)))
		}

	@Test
	fun `should override short url when same identifier has been used for every encoding`(softly: SoftAssertions) =
		runTest {
			val storageSeed = mutableMapOf<String, Pair<URI, URI>>()
			val testStorage = spyk(HashStorage(storageSeed))
			val codec =
				Base62Codec(
					scheme = SCHEME,
					host = HOST,
					idValueGenerator = { TWITTER_EPOCH },
					storage = testStorage,
				)

			val longUrlOne = URI.create("${TEST_LONG_URL}-1")
			val actualResultOne = codec.encode(longUrlOne)
			val longUrlTwo = URI.create("${TEST_LONG_URL}-2")
			val actualResultTwo = codec.encode(longUrlTwo)
			val longUrlThree = URI.create("${TEST_LONG_URL}-3")
			val actualResultThree = codec.encode(longUrlThree)

			val expectedHashValue = HASH_VALUE
			val expectedResult = URI.create("https://tinyurl.com/$expectedHashValue")

			verifyOrder {
				testStorage.put(expectedHashValue, longUrlOne to expectedResult)
				testStorage.put(expectedHashValue, longUrlTwo to expectedResult)
				testStorage.put(expectedHashValue, longUrlThree to expectedResult)
			}

			softly.assertThat(actualResultOne).isEqualTo(expectedResult)
			softly.assertThat(actualResultTwo).isEqualTo(expectedResult)
			softly.assertThat(actualResultThree).isEqualTo(expectedResult)
			softly.assertThat(storageSeed).isEqualTo(mapOf(expectedHashValue to (longUrlThree to expectedResult)))
		}

	@Test
	fun `should decode short url and retrieve original (long) one`() =
		runTest {
			val givenHashValue = HASH_VALUE
			val givenUrl = URI.create("https://tinyurl.com/$givenHashValue")
			val expectedResult = URI.create(TEST_LONG_URL)

			val codec =
				Base62Codec(
					scheme = SCHEME,
					host = HOST,
					idValueGenerator = mockk(),
					storage =
						mockk {
							every { this@mockk.get(givenHashValue) } returns (expectedResult to givenUrl)
						},
				)

			val actualResult = codec.decode(givenUrl)

			assertThat(actualResult).isEqualTo(expectedResult)
		}

	@Test
	fun `should retrieve nothing (null) when given short url does not match any entry`() =
		runTest {
			val givenHashValue = HASH_VALUE
			val givenUrl = URI.create("https://tinyurl.com/$givenHashValue")

			val codec =
				Base62Codec(
					scheme = SCHEME,
					host = HOST,
					idValueGenerator = mockk(),
					storage =
						mockk {
							every { this@mockk.get(givenHashValue) } returns null
						},
				)

			val actualResult = codec.decode(givenUrl)

			assertThat(actualResult).isNull()
		}
}
