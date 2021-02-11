package com.ahold.commerce.integration.messageProvider

import java.time.LocalDateTime

abstract class AbstractMessagePublisher {
    abstract fun publishMessage(destinationName: String, destination: String, objectId: Any, message: Any, publicationDateTime: LocalDateTime)
}