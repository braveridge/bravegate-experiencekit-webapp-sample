package com.braveridge.experiencekit

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cache.annotation.EnableCaching

@SpringBootApplication
@EnableCaching
class SampleApplication

fun main(args: Array<String>) {
    runApplication<SampleApplication>(*args)
}