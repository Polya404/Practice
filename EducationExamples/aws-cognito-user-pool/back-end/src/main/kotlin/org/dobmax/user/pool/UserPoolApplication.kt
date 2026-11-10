package org.dobmax.user.pool

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class UserPoolApplication

@Suppress("SpreadOperator")
fun main(args: Array<String>) {
    runApplication<UserPoolApplication>(*args)
}
