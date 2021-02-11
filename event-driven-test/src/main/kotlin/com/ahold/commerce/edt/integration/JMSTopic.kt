package com.ahold.commerce.edt.integration

import com.ahold.commerce.integration.messageProvider.AbstractMessagePublisher
import com.ahold.commerce.edt.service.Service
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class JMSTopic(@Qualifier("um-publisher") private val jmsTopic: AbstractMessagePublisher,
               private val service: Service,
               @Value("\${ahold.integration.jms.jndi.publish.topic.name}")
               private val topicName: String,
               @Value("\${ahold.integration.jms.jndi.publish.topic.destination")
               private val destination: String) {

    fun publishMessage(payload: Any) {
        service.publishMessage(jmsTopic, topicName, destination, payload)
    }
}