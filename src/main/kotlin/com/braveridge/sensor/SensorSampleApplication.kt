package com.braveridge.sensor

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cache.annotation.EnableCaching

@SpringBootApplication
@EnableCaching
class SensorSampleApplication

fun main(args: Array<String>) {
	runApplication<SensorSampleApplication>(*args)
}