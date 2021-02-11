package com.ahold.commerce.edt.controller.v1

import com.ahold.commerce.edt.integration.JMSQueue
import com.ahold.commerce.edt.integration.JMSTopic
import org.springframework.web.bind.annotation.*

@RestController
class Controller(private val jmsTopic: JMSTopic, private val jmsQueue: JMSQueue) {
    @RequestMapping(value = ["/api/postEvents/{type}"], method = [RequestMethod.POST])
    fun getMessage(@PathVariable type: String, @RequestBody payload: String) {
        when(type) {
            "jmsTopic"  ->  jmsTopic.publishMessage(payload)
            "jmsQueue"  ->  jmsQueue.publishMessage(payload)
            else    ->  "No publisher found"
        }
    }
}
