package io.maksymdobrynin.async.kafka.consumer

import io.maksymdobrynin.async.kafka.api.ChargeRequest
import io.maksymdobrynin.async.kafka.processor.ChargeProcessor
import jakarta.validation.ConstraintViolationException
import jakarta.validation.Validator
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.kafka.annotation.DltHandler
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.annotation.RetryableTopic
import org.springframework.kafka.retrytopic.SameIntervalTopicReuseStrategy
import org.springframework.kafka.retrytopic.TopicSuffixingStrategy
import org.springframework.kafka.support.Acknowledgment
import org.springframework.kafka.support.KafkaHeaders
import org.springframework.messaging.handler.annotation.Header
import org.springframework.retry.annotation.Backoff
import org.springframework.stereotype.Component

@Component
class InboundTransactionsListener(
    private val paymentProcessor: ChargeProcessor,
    private val validator: Validator,
) {

    private val log: Logger = LoggerFactory.getLogger(this::class.java)

    @RetryableTopic(
        attempts = "3",
        backoff = Backoff(
            delay = 5000,
            multiplier = 2.0,
            maxDelay = 60000
        ),
        numPartitions = "\${app.kafka.topics.partitions}",
        topicSuffixingStrategy = TopicSuffixingStrategy.SUFFIX_WITH_DELAY_VALUE,
        sameIntervalTopicReuseStrategy = SameIntervalTopicReuseStrategy.MULTIPLE_TOPICS,
        exclude = [ConstraintViolationException::class],
    )
    @KafkaListener(
        topics = ["\${app.kafka.topics.name}"],
    )
    fun doListen(record: ConsumerRecord<String, ChargeRequest>, ack: Acknowledgment) {
        try {
            validator.validate(record.value()).takeIf { it.isNotEmpty() }
                ?.also {
                    throw ConstraintViolationException(it)
                }

            paymentProcessor.doProcess(record.value())

            ack.acknowledge()
        } catch (ex: ConstraintViolationException) {
            log.atError()
                .log(
                    "Record failed: {}",
                    ex.constraintViolations.map {
                        "property=${it.propertyPath}, value=${it.invalidValue}, message=${it.message}"
                    }
                )
            throw ex
        } catch (ex: Exception) {
            log.atError()
                .log("Payment request can't be processed: key={}, value={}", record.key(), record.value())
            throw ex
        }
    }

    @DltHandler
    fun handleDlt(
        record: ConsumerRecord<String, ChargeRequest>,
        @Header(KafkaHeaders.EXCEPTION_MESSAGE) exceptionMessage: String?
    ) {
        log.atError().log(
            "Toxic message received. key={}, value={}, cause={}",
            record.key(), record.value(), exceptionMessage,
        )
    }
}