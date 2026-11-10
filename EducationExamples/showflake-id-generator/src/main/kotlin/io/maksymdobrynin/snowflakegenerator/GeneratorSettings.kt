package io.maksymdobrynin.snowflakegenerator

data class GeneratorSettings(
	// 00:00:00 on January 1st, 2000 (UTC)
	var startingEpoch: Long = 946684800L,
	var datacenterId: Long = 1,
	var workedId: Long = 1,
	var nextTimeSeed: () -> Long = { System.currentTimeMillis() },
	var sequence: Long = 0L,
)
