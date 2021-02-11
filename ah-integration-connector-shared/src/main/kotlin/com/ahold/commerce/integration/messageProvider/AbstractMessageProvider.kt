package com.ahold.commerce.integration.messageProvider

abstract class AbstractMessageProvider {
    var messageListener: MessageListener? = null
       /* get() = messageListener
        set(messageListener: MessageListener) { this.messageListener = messageListener }*/

    abstract fun sendMessage(destination: String, payload: Any)
    abstract fun receive(payload: Any)
    abstract fun subscribeMessage(): String
}