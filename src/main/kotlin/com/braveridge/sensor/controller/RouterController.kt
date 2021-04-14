package com.braveridge.sensor.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class RouterController {
    @GetMapping("/router")
    fun edit():String {
        return "router/edit"
    }
}