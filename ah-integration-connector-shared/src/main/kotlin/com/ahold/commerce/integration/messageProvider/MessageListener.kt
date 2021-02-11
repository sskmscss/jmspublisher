package com.ahold.commerce.integration.messageProvider

interface MessageListener {
    fun receive(type: String, payload: Any)
}
