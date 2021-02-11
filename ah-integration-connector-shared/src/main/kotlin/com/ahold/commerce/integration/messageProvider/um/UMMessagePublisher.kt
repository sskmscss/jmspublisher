package com.ahold.commerce.integration.messageProvider.um


import com.ahold.commerce.integration.configuration.ConfigProperties
import com.ahold.commerce.integration.messageProvider.AbstractMessagePublisher
import com.ahold.commerce.integration.messageProvider.um.jms.JmsHeadersFactory
import com.ahold.commerce.integration.messageProvider.um.jms.model.JmsHeaders
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.jms.core.JmsTemplate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.jms.Message
import kotlin.reflect.full.memberProperties

class UMMessagePublisher(
        private val configProperties: ConfigProperties,
        private val jmsTemplate: JmsTemplate,
        private val objectMapper: ObjectMapper,
        private val jmsHeadersFactory: JmsHeadersFactory
) : AbstractMessagePublisher() {

    override fun publishMessage(destinationName: String, destination: String,
                             objectId: Any, message: Any, publicationDateTime: LocalDateTime) {
        jmsTemplate.send(destinationName) { session ->
            val converter = jmsTemplate.messageConverter
                    ?: throw IllegalStateException("No 'messageConverter' specified. Check configuration of JmsTemplate.")
            val headers = jmsHeadersFactory.create(destination, objectId, publicationDateTime)
            converter.toMessage(objectMapper.writeValueAsString(message), session).withHeaders(headers)
        }
    }


    private fun Message.withHeaders(jmsHeaders: JmsHeaders): Message {
        JmsHeaders::class.memberProperties.map { property ->
            property.name to property.get(jmsHeaders)
        }.forEach { (name, value) ->
            value?.let {
                if (value is LocalDateTime) {
                    setObjectProperty(name, DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(value))
                } else {
                    setObjectProperty(name, value)
                }
            }
        }
        return this
    }

}
