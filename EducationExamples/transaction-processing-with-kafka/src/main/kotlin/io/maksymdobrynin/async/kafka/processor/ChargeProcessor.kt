package io.maksymdobrynin.async.kafka.processor

import io.maksymdobrynin.async.kafka.api.ChargeRequest
import java.math.BigDecimal
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class ChargeProcessor : PaymentProcessor<ChargeRequest> {

    private val log: Logger = LoggerFactory.getLogger(this::class.java)

    override fun doProcess(chargeRequest: ChargeRequest) {
        CoroutineScope(Dispatchers.IO).launch {
            chargeRequest.amount?.let {
                if (it.remainder(BigDecimal(20)).compareTo(BigDecimal.ZERO) == 0) {
                    // successful processing, so far do nothing
                    log.atInfo().log("OK!")
                } else {
                    log.atError().log("NOK!")
                    // failed processing, send to retry queue
                }
            } ?: log.atError().log("NOK!!")
        }
    }
}