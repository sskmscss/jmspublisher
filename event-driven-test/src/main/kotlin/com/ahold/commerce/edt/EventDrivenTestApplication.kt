package com.ahold.commerce.edt

import com.ahold.commerce.edt.service.RxBus
import khttp.post
import org.json.JSONObject
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.ConfigurableApplicationContext

@SpringBootApplication(scanBasePackages = ["com.ahold.commerce.edt", "com.ahold.commerce.integration"])

class EventDrivenTestApplication

fun main(args: Array<String>) {
	val ctx: ConfigurableApplicationContext = runApplication<EventDrivenTestApplication>(*args)

	RxBus.listen(JSONObject::class.java).subscribe {
		try {
			when (it.optString("type")) {

				"kafka" -> {
					println("KAFKA SUBSCRIBED MESSAGE :: " + it.optString("data"))
				}
				"jmsTopic" -> {
					println("WEBM TOPIC SUBSCRIBED MESSAGE :: " + it.optString("data"))
				}
				"jmsQueue" -> {
					println("WEBM QUEUE SUBSCRIBED MESSAGE :: " + it.optString("data"))
				}
			}
		} catch(e: Exception) {
			println(e)
		}
	}
}
