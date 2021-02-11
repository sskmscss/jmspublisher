package com.ahold.commerce.integration.messageProvider.um.jms

import com.ahold.commerce.integration.constant.Constants.TRANSACTION_ID_HEADER
import com.ahold.commerce.integration.messageProvider.um.jms.model.JmsHeaders
import com.ahold.commerce.integration.messageProvider.um.jms.properties.JmsHeadersProperties
import org.apache.logging.log4j.ThreadContext
import org.slf4j.MDC
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
@EnableConfigurationProperties(JmsHeadersProperties::class)
class JmsHeadersFactory (
        private val jmsHeadersProperties: JmsHeadersProperties
        ){

    fun create(destination: String, objectId: Any, publicationDateTime: LocalDateTime): JmsHeaders =
            JmsHeaders(
                    eventId = ThreadContext.get(TRANSACTION_ID_HEADER),
                    destination = destination,
                    source = jmsHeadersProperties.source,
                    sourceId = jmsHeadersProperties.sourceId,
                    objectId = objectId,
                    publicationDateTime = publicationDateTime
            )
}