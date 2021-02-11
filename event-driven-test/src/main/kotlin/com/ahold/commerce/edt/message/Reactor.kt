package com.ahold.commerce.edt.message

import com.ahold.commerce.integration.messageProvider.MessageListener
import com.ahold.commerce.edt.service.RxBus
import org.springframework.stereotype.Component
import org.json.JSONObject

@Component
class Reactor : MessageListener {
    override fun receive(type: String, payload: Any) {
        println("CallBack to the caller method $type $payload")
        when(type) {
            "kafka" ->  {
                val json = JSONObject()
                json.put("type", "kafka")
                json.put("data", payload)
                RxBus.publish(json)
            }
            "um"    ->  {
                val json= JSONObject()
                json.put("type", "um")
                json.put("data", payload)
                RxBus.publish(json)
            }
            "webmTopic"    ->  {
                val json= JSONObject()
                json.put("type", "webmTopic")
                json.put("data", payload)
                RxBus.publish(json)
            }
            "webmQueue"    ->  {
                val json= JSONObject()
                json.put("type", "webmQueue")
                json.put("data", payload)
                RxBus.publish(json)
            }
        }
    }
}