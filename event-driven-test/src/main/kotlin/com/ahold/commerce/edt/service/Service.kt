package com.ahold.commerce.edt.service

import com.ahold.commerce.edt.constant.DefaultValues
import com.ahold.commerce.integration.messageProvider.AbstractMessageProvider
import com.ahold.commerce.integration.messageProvider.AbstractMessagePublisher
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.time.ZoneId

@Service
class Service {

    fun publishMessage(serviceCall: AbstractMessagePublisher,
                           topicOrQueue: String, destination: String, msg: Any) {
        serviceCall.publishMessage(topicOrQueue, destination, 1, msg,
                LocalDateTime.now(ZoneId.of(DefaultValues.DEFAULT_TIME_ZONE)))
    }

    fun subscribeMessage(serviceCall: AbstractMessageProvider, type: String): String? {
        return serviceCall.subscribeMessage()
    }
}
