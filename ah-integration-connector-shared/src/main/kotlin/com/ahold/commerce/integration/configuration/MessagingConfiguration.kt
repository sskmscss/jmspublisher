package com.ahold.commerce.integration.configuration

import com.ahold.commerce.integration.messageProvider.AbstractMessageProvider
import com.ahold.commerce.integration.messageProvider.AbstractMessagePublisher
import com.ahold.commerce.integration.messageProvider.kafka.KafkaMessageProvider
import com.ahold.commerce.integration.messageProvider.um.UMMessagePublisher
import com.ahold.commerce.integration.messageProvider.um.jms.JmsHeadersFactory
import com.fasterxml.jackson.databind.ObjectMapper
import org.apache.logging.log4j.kotlin.logger
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jms.core.JmsTemplate
import javax.annotation.PreDestroy
import kotlin.jvm.Throws

@Configuration
class MessagingConfiguration(private val configProperties: ConfigProperties) {

    @Bean
    @Qualifier("kafka")
    fun messageProviderKafka(): AbstractMessageProvider {
        logger().info("KafkaMessageProvider")
        return KafkaMessageProvider(configProperties)
    }

    @Bean
    @Qualifier("um-publisher")
    @ConditionalOnProperty(value = ["ahold.integration.jms.jndi.publish.queue.enabled"], havingValue = "true")
    fun messagePublisherQueueUM(
            queuePublishJmsTemplate: JmsTemplate,
            objectMapper: ObjectMapper,
            jmsHeadersFactory: JmsHeadersFactory
    ): AbstractMessagePublisher =
         UMMessagePublisher(configProperties, queuePublishJmsTemplate,objectMapper,jmsHeadersFactory)

    @Bean
    @Qualifier("um-publisher")
    @ConditionalOnProperty(value = ["ahold.integration.jms.jndi.publish.topic.enabled"], havingValue = "true")
    fun messagePublisherTopicUM(
            topicPublishJmsTemplate: JmsTemplate,
            objectMapper: ObjectMapper,
            jmsHeadersFactory: JmsHeadersFactory
    ): AbstractMessagePublisher =
            UMMessagePublisher(configProperties, topicPublishJmsTemplate,objectMapper,jmsHeadersFactory)

    @PreDestroy
    @Throws(Exception::class)
    fun onDestroy() {
        logger().info("Spring Container is destroyed!")
    }
}
