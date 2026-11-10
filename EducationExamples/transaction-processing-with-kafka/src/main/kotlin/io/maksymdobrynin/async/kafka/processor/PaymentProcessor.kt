package io.maksymdobrynin.async.kafka.processor

interface PaymentProcessor<T> {
    fun doProcess(chargeRequest: T)
}