package com.maksym.dobrynin.kubernetesdemo

import org.springframework.beans.factory.annotation.Value
import org.springframework.http.server.reactive.ServerHttpRequest
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class HelloWorldController {

    @Value("\${POD_NAME:unknown}")
    private val podName: String? = null

    @GetMapping
    fun index(request: ServerHttpRequest): String =
        "Hello, world from ${podName}!"
}

