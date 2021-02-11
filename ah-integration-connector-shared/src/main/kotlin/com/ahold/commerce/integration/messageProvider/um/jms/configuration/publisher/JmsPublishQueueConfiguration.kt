package com.ahold.commerce.integration.messageProvider.um.jms.configuration.publisher

import com.ahold.commerce.integration.messageProvider.um.jms.configuration.JmsJndiProperties
import org.apache.logging.log4j.kotlin.logger
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jms.annotation.EnableJms
import org.springframework.jms.core.JmsTemplate
import org.springframework.jndi.JndiObjectFactoryBean
import java.util.*
import javax.jms.ConnectionFactory
import javax.naming.Context

@EnableJms
@Configuration
@ConditionalOnProperty(value = ["ahold.integration.jms.jndi.publish.queue.enabled"], havingValue = "true")
class JmsPublishQueueConfiguration(
        private val jmsPublishQueueJndiProperties: JmsJndiProperties
) {

    @Bean
    @ConditionalOnMissingBean(name = ["queuePublishJmsTemplate"])
    fun queueJmsTemplate() = JmsTemplate().apply {
        connectionFactory = queueConnectionFactory()
    }
    /*@Bean
    @ConditionalOnMissingBean(name = ["queueJmsListenerContainerFactory"])
    fun queueJmsListenerContainerFactory() = DefaultJmsListenerContainerFactory().apply {
        setConnectionFactory(queueConnectionFactory())
    }*/

    @Bean
    fun queueConnectionFactory(): ConnectionFactory {
        val connectionFactory = jmsPublishQueueJndiProperties.connectionFactory
        val initialContextFactory = jmsPublishQueueJndiProperties.initialContextFactory
        logger().info("Building connection factory {} with initial context factory {}$connectionFactory $initialContextFactory")

        return with(JndiObjectFactoryBean()) {
            jndiName = connectionFactory
            jndiEnvironment = queueJndiProperties()
            afterPropertiesSet()
            `object` as ConnectionFactory
        }
    }

    @Bean
    fun queueJndiProperties() = Properties().apply {
        put(Context.INITIAL_CONTEXT_FACTORY, jmsPublishQueueJndiProperties.initialContextFactory)
        put(Context.PROVIDER_URL, jmsPublishQueueJndiProperties.providerUrl)
    }
}