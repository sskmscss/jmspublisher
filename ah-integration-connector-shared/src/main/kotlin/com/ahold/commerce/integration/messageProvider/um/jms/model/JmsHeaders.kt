package com.ahold.commerce.integration.messageProvider.um.jms.model

import java.time.LocalDateTime

data class JmsHeaders(
        val eventId: String?,
        val sourceId: String,
        val source: String,
        val destination: String,
        val objectId: Any,
        val publicationDateTime: LocalDateTime
)
