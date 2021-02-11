package com.ahold.commerce.integration.messageProvider.kafka

import com.ahold.commerce.integration.configuration.ConfigProperties
import com.ahold.commerce.integration.messageProvider.AbstractMessageProvider
import org.springframework.kafka.annotation.EnableKafka
import java.io.Closeable

@EnableKafka
class KafkaMessageProvider(
        private val configProperties: ConfigProperties) : AbstractMessageProvider(), Closeable {
    override fun sendMessage(destination: String, payload: Any) {
        TODO("Not yet implemented")
    }

    override fun receive(payload: Any) {
        TODO("Not yet implemented")
    }

    override fun subscribeMessage(): String {
        TODO("Not yet implemented")
    }


    override fun close() {
        TODO("Not yet implemented")
    }
}
