package io.maksymdobrynin.snowflakegenerator

internal const val LENGTH_64_BIT = 64L

internal object BitwiseUtils {
	/**
	 * Extracts a subset of bits from the given source number starting at a specified bit position.
	 *
	 * @param src The source number from which bits are to be extracted.
	 * @param len The length of the source number in bits. Defaults to 64 bits.
	 * @param startBit The starting bit position (0-based) for the extraction.
	 * @param bitCount The number of bits to extract from the source.
	 * @return The extracted bits as a `Long` value.
	 * @throws IllegalArgumentException If the starting bit is out of range, the bit count is invalid,
	 * or the combination of starting bit and bit count exceeds the length of the source number.
	 */
	fun extract(
		src: Long,
		len: Long = LENGTH_64_BIT,
		startBit: Int,
		bitCount: Int,
	): Long {
		require(startBit in 0..<len) {
			"Starting bit must match range 0..${len - 1}, otherwise it will be out of bounds."
		}
		require(bitCount in 1..len) {
			"Count of bits to be extracted must match range of a given number that is 1..$len."
		}
		require(startBit + bitCount <= len) {
			"Starting bit in combination with count of bits to be extracted [${startBit + bitCount}] must not exceed " +
				"the length of a given number: $len."
		}

		val mask = (1L shl bitCount) - 1
		return (src ushr startBit) and mask
	}
}
