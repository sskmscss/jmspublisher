package com.ahold.commerce.integration.messageProvider.um.jms

import com.ahold.commerce.integration.constant.Constants.TRANSACTION_ID_HEADER
import com.ahold.commerce.integration.messageProvider.um.jms.properties.JmsHeadersProperties
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test
import org.apache.logging.log4j.ThreadContext
import java.time.LocalDateTime
import java.time.ZoneId

@Tag("unit-tests")
class JmsHeadersFactoryTest {

    private val jmsHeadersProperties = JmsHeadersProperties(
            sourceId = "id",
            source = "s"
    )
    private val factory = JmsHeadersFactory(jmsHeadersProperties)

    @BeforeEach
    fun init() {
        ThreadContext.put(TRANSACTION_ID_HEADER, "transaction")
    }

    @Test
    fun shouldCreateHeadersWithTransactionId() {
        val beforeInCet = LocalDateTime.now(ZoneId.of("CET")).minusSeconds(1)
        val actual = factory.create("dest", 1L, LocalDateTime.now(ZoneId.of("CET")))
        val afterInCet = LocalDateTime.now(ZoneId.of("CET")).plusSeconds(1)

        assertThat(actual).`as`("Check headers").matches {
            it.destination == "dest" &&
                    it.eventId == "transaction" &&
                    it.objectId == 1L &&
                    it.publicationDateTime.isBefore(afterInCet) &&
                    it.publicationDateTime.isAfter(beforeInCet) &&
                    it.source == "s" &&
                    it.sourceId == "id"
        }
    }
}
