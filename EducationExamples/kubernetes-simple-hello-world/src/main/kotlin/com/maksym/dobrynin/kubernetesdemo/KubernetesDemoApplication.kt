package com.maksym.dobrynin.kubernetesdemo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class KubernetesDemoApplication

fun main(args: Array<String>) {
    runApplication<KubernetesDemoApplication>(*args)
}
