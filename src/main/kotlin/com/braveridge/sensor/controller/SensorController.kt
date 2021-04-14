package com.braveridge.sensor.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.stereotype.Controller

@Controller
class SensorController {
    @GetMapping("")
    fun index():String {
        return "index"
    }
}