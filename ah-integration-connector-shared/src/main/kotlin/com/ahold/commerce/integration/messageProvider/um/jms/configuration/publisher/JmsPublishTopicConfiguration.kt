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
@ConditionalOnProperty(value = ["ahold.integration.jms.jndi.publish.topic.enabled"], havingValue = "true")
class JmsPublishTopicConfiguration (
        private val jmsPublishTopicJndiProperties: JmsJndiProperties
        ){
    @Bean
    @ConditionalOnMissingBean(name = ["topicPublishJmsTemplate"])
    fun topicJmsTemplate() = JmsTemplate().apply {
        connectionFactory = topicConnectionFactory()
    }
    /*@Bean
    @ConditionalOnMissingBean(name = ["topicJmsListenerContainerFactory"])
    fun topicJmsListenerContainerFactory() = DefaultJmsListenerContainerFactory().apply {
        setConnectionFactory(topicConnectionFactory())
    }*/

    @Bean
    fun topicConnectionFactory(): ConnectionFactory {
        val connectionFactory = jmsPublishTopicJndiProperties.connectionFactory
        val initialContextFactory = jmsPublishTopicJndiProperties.initialContextFactory
        logger().info("Building connection factory {} with initial context factory {}$connectionFactory $initialContextFactory")

        return with(JndiObjectFactoryBean()) {
            jndiName = connectionFactory
            jndiEnvironment = topicJndiProperties()
            afterPropertiesSet()
            `object` as ConnectionFactory
        }
    }

    @Bean
    fun topicJndiProperties() = Properties().apply {
        put(Context.INITIAL_CONTEXT_FACTORY, jmsPublishTopicJndiProperties.initialContextFactory)
        put(Context.PROVIDER_URL, jmsPublishTopicJndiProperties.providerUrl)
    }
}