package com.ahold.commerce.integration.configuration

import com.ahold.commerce.integration.messageProvider.um.jms.configuration.JmsJndiProperties
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Lazy
import org.springframework.stereotype.Component

@Component
class ConfigProperties() {
    @Bean
    @Lazy
    @ConfigurationProperties("ahold.integration.jms.jndi.publish.queue")
    fun jmsPublishQueueJndiProperties() = JmsJndiProperties()

    @Bean
    @Lazy
    @ConfigurationProperties("aahold.integration.jms.jndi.publish.topic")
    fun jmsPublishTopicJndiProperties() = JmsJndiProperties()
}