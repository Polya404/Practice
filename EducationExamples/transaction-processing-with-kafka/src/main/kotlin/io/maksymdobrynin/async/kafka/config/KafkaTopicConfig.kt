package io.maksymdobrynin.async.kafka.config

import org.apache.kafka.clients.admin.AdminClientConfig
import org.apache.kafka.clients.admin.NewTopic
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.kafka.core.KafkaAdmin
import org.springframework.stereotype.Component


/**
 * This is for demo (testing purpose) only, in production Kafka Topics should be initialized with
 * dependency on an application running whether as producer or consumer.
 */
@Component
class KafkaTopicConfig {

    @Bean
    fun kafkaAdmin(
        @Value(value = "\${spring.kafka.bootstrap-servers}") bootstrapAddress: String
    ): KafkaAdmin {
        val config: MutableMap<String, Any?> = HashMap()
        config[AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG] = bootstrapAddress
        return KafkaAdmin(config)
    }

    /**
     * For a testing purpose only, otherwise don't tie topic instantiation with application level.
     * Use kafka scripts instead to manage topics and other stuff.
     */
    @Bean
    fun inboundTransactionsTopic(
        @Value(value = "\${app.kafka.topics.name}") topicName: String,
        @Value(value = "\${app.kafka.topics.partitions}") numPartitions: Int,
    ): NewTopic {
        return NewTopic(topicName, numPartitions, 1)
    }
}