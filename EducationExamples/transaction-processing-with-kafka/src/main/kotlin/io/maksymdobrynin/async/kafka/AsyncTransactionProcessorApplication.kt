package io.maksymdobrynin.async.kafka

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class DeadLetterExchangeApplication

fun main(args: Array<String>) {
	runApplication<DeadLetterExchangeApplication>(*args)
}
