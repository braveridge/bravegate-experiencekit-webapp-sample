package com.braveridge.sensor

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
class SensorSampleApplication

fun main(args: Array<String>) {
	runApplication<SensorSampleApplication>(*args)
}