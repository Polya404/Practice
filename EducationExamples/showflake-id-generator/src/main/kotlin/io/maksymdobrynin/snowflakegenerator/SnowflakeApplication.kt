package io.maksymdobrynin.snowflakegenerator

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableConfigurationProperties
open class SnowflakeApplication

fun main(vararg args: String) {
	runApplication<SnowflakeApplication>(*args)
}
