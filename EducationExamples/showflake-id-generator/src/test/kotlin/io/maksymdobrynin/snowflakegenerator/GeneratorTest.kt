package io.maksymdobrynin.snowflakegenerator

import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatCode
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import java.util.concurrent.atomic.AtomicLong

class GeneratorTest {
	@ParameterizedTest
	@ValueSource(longs = [-4, -3, -2, -1])
	fun `Should fail to init Generator when given time epoch doesn't match range`(invalidStartingTimeEpoch: Long) {
		assertThatCode {
			Generator(
				GeneratorSettings(
					startingEpoch = invalidStartingTimeEpoch,
					datacenterId = 1,
					workedId = 1,
				),
			)
		}
			.isExactlyInstanceOf(IllegalArgumentException::class.java)
			.hasMessage("Starting time epoch must match range 0 .. ${Long.MAX_VALUE}")
	}

	@ParameterizedTest
	@ValueSource(longs = [-5, -4, -3, -2, -1, 0, 32, 33, 34, 35, 36])
	fun `Should fail to init Generator when given Datacenter ID doesn't match range`(invalidDatacenterId: Long) {
		assertThatCode {
			Generator(
				GeneratorSettings(
					datacenterId = invalidDatacenterId,
					workedId = 1,
				),
			)
		}
			.isExactlyInstanceOf(IllegalArgumentException::class.java)
			.hasMessage("Datacenter ID must match range 1 .. 31")
	}

	@ParameterizedTest
	@ValueSource(longs = [-5, -4, -3, -2, -1, 0, 32, 33, 34, 35, 36])
	fun `Should fail to instantiate Generator when given Worker ID doesn't match possible range`(invalidWorkerId: Long) {
		assertThatCode {
			Generator(
				GeneratorSettings(
					datacenterId = 1,
					workedId = invalidWorkerId,
				),
			)
		}
			.isExactlyInstanceOf(IllegalArgumentException::class.java)
			.hasMessage("Worker ID must match range 1 .. 31")
	}

	@ParameterizedTest
	@ValueSource(longs = [-5, -4, -3, -2, -1, 4096, 4097, 4098, 4099, 4100])
	fun `Should fail to instantiate Generator when given Sequence doesn't match possible range`(invalidSequence: Long) {
		assertThatCode {
			Generator(
				GeneratorSettings(
					datacenterId = 1,
					workedId = 1,
					sequence = invalidSequence,
				),
			)
		}
			.isExactlyInstanceOf(IllegalArgumentException::class.java)
			.hasMessage("Sequence must match range 0 .. 4095")
	}

	@Test
	fun `Should generate deterministic identifier based on given parameters`() =
		runTest {
			val deterministicDatacenterId = 1L
			val deterministicWorkerId = 1L
			val deterministicStartingEpoch = 0L // 00:00:00 UTC on January 1, 1970
			val deterministicSequence = 0L
			val nextTimeSeed = 1L // 00:00:01 UTC on January 1, 1970

			val actualResult =
				deterministicGenerator(
					deterministicDatacenterId,
					deterministicWorkerId,
					deterministicStartingEpoch,
					deterministicSequence,
				) { nextTimeSeed }
					.nextId()

			val expectedResult =
				deterministicIdentifier(
					nextTimeSeed,
					deterministicStartingEpoch,
					deterministicDatacenterId,
					deterministicWorkerId,
					deterministicSequence,
				)

			assertThat(actualResult)
				.describedAs("Generated ID should be deterministic based on given parameters.")
				.isEqualTo(expectedResult)
		}

	@Test
	fun `Should generate unique deterministic id based on given parameters when call multiple times`() =
		runTest {
			val deterministicDatacenterId = 1L
			val deterministicWorkerId = 1L
			val deterministicStartingEpoch = 0L // 00:00:00 UTC on January 1, 1970
			val deterministicSequence = 0L
			val nextTimeSeed = 1L // Start with 00:00:01 UTC on January 1, 1970
			val nextTimeGenerator = AtomicLong(nextTimeSeed)

			val generator =
				deterministicGenerator(
					deterministicDatacenterId,
					deterministicWorkerId,
					deterministicStartingEpoch,
					deterministicSequence,
				) { nextTimeGenerator.getAndIncrement() }

			val actualResult1 = generator.nextId()
			val expectedResult1 =
				deterministicIdentifier(
					nextTimeSeed,
					deterministicStartingEpoch,
					deterministicDatacenterId,
					deterministicWorkerId,
					deterministicSequence,
				)

			assertThat(actualResult1)
				.describedAs("Generated ID [Attempt #1] should be deterministic based on given parameters.")
				.isEqualTo(expectedResult1)

			val actualResult2 = generator.nextId()
			val expectedResult2 =
				deterministicIdentifier(
					nextTimeSeed + nextTimeSeed,
					deterministicStartingEpoch,
					deterministicDatacenterId,
					deterministicWorkerId,
					deterministicSequence,
				)

			assertThat(actualResult2)
				.describedAs("Generated ID [Attempt #2] should be deterministic based on given parameters.")
				.isEqualTo(expectedResult2)
		}

	@Test
	fun `Should ensure the Generator produces unique IDs when accessed concurrently by multiple threads`() =
		runTest {
			val deterministicDatacenterId = 1L
			val deterministicWorkerId = 1L
			val deterministicStartingEpoch = 0L // 00:00:00 UTC on January 1, 1970
			val deterministicSequence = 0L
			val nextTimeSeed = 1L // Start with 00:00:01 UTC on January 1, 1970
			val nextTimeGenerator = AtomicLong(nextTimeSeed)

			val generator =
				deterministicGenerator(
					deterministicDatacenterId,
					deterministicWorkerId,
					deterministicStartingEpoch,
					deterministicSequence,
				) { nextTimeGenerator.getAndIncrement() }

			val expectedIds = mutableSetOf<Long>()
			for (i in 0 until 5) {
				expectedIds.add(
					deterministicIdentifier(
						nextTimeSeed + i,
						deterministicStartingEpoch,
						deterministicDatacenterId,
						deterministicWorkerId,
						deterministicSequence,
					),
				)
			}

			val actualIds = mutableSetOf<Long>()
			coroutineScope {
				repeat(5) {
					launch {
						actualIds.add(generator.nextId())
					}
				}
			}

			assertThat(actualIds)
				.containsExactlyInAnyOrderElementsOf(expectedIds)
		}

	@Test
	fun `Should generate deterministic identifier with incremented sequence when accessed for same timestamp`() =
		runTest {
			val deterministicDatacenterId = 1L
			val deterministicWorkerId = 1L
			val deterministicStartingEpoch = 0L // 00:00:00 UTC on January 1, 1970
			val deterministicSequence = 0L
			val nextTimeSeed = 0L // 00:00:01 UTC on January 1, 1970

			val actualResult =
				deterministicGenerator(
					deterministicDatacenterId = deterministicDatacenterId,
					deterministicWorkerId = deterministicWorkerId,
					deterministicStartingEpoch = deterministicStartingEpoch,
					deterministicSequence = deterministicSequence,
				) { nextTimeSeed }
					.nextId()

			val expectedResult =
				deterministicIdentifier(
					nextTimeSeed = nextTimeSeed,
					deterministicStartingEpoch = deterministicStartingEpoch,
					deterministicDatacenterId = deterministicDatacenterId,
					deterministicWorkerId = deterministicWorkerId,
					deterministicSequence = deterministicSequence + 1,
				)

			assertThat(actualResult)
				.describedAs("Generated ID should be deterministic based on given parameters.")
				.isEqualTo(expectedResult)
		}

	@Test
	fun `Should generate deterministic identifier with new timestamp when sequence exceed for same timestamp`() =
		runTest {
			val deterministicDatacenterId = 1L
			val deterministicWorkerId = 1L
			val deterministicStartingEpoch = 0L // 00:00:00 UTC on January 1, 1970
			val deterministicSequence = 4095L
			val nextTimeSeed = 0L // 00:00:01 UTC on January 1, 1970
			val nextTimeGenerator = AtomicLong(-1)

			val actualResult =
				deterministicGenerator(
					deterministicDatacenterId,
					deterministicWorkerId,
					deterministicStartingEpoch,
					deterministicSequence,
				) { nextTimeGenerator.incrementAndGet() }
					.nextId()

			val expectedResult =
				deterministicIdentifier(
					nextTimeSeed + 1,
					deterministicStartingEpoch,
					deterministicDatacenterId,
					deterministicWorkerId,
					0,
				)

			assertThat(actualResult)
				.describedAs("Generated ID should be deterministic based on given parameters.")
				.isEqualTo(expectedResult)
		}

	@Test
	fun `Should fail due to timeout while generating new timestamp when sequence exceeded for same timestamp`() {
		val deterministicDatacenterId = 1L
		val deterministicWorkerId = 1L
		val deterministicStartingEpoch = 0L // 00:00:00 UTC on January 1, 1970
		val deterministicSequence = 4095L
		val nextTimeSeed = 0L // 00:00:01 UTC on January 1, 1970

		assertThatCode {
			runTest {
				deterministicGenerator(
					deterministicDatacenterId,
					deterministicWorkerId,
					deterministicStartingEpoch,
					deterministicSequence,
				) { nextTimeSeed }
					.nextId()
			}
		}
			.isExactlyInstanceOf(TimeoutCancellationException::class.java)
			.hasMessageStartingWith("Timed out after 3s of")
	}

	private fun deterministicGenerator(
		deterministicDatacenterId: Long,
		deterministicWorkerId: Long,
		deterministicStartingEpoch: Long,
		deterministicSequence: Long,
		nextTimeSeed: () -> Long,
	): Generator =
		Generator(
			GeneratorSettings(
				datacenterId = deterministicDatacenterId,
				workedId = deterministicWorkerId,
				startingEpoch = deterministicStartingEpoch,
				sequence = deterministicSequence,
				nextTimeSeed = nextTimeSeed,
			),
		)

	private fun deterministicIdentifier(
		nextTimeSeed: Long,
		deterministicStartingEpoch: Long,
		deterministicDatacenterId: Long,
		deterministicWorkerId: Long,
		deterministicSequence: Long,
	): Long =
		((nextTimeSeed - deterministicStartingEpoch) shl 22) or
			(deterministicDatacenterId shl 17) or
			(deterministicWorkerId shl 12) or
			deterministicSequence
}
