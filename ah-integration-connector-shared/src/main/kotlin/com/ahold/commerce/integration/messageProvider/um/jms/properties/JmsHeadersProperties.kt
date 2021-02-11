package com.ahold.commerce.integration.messageProvider.um.jms.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties("ahold.integration.jms.jndi.headers")
data class JmsHeadersProperties(
        val sourceId: String,
        val source: String
)
