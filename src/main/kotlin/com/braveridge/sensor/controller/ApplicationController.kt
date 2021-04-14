package com.braveridge.sensor.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.stereotype.Controller

@Controller
class ApplicationController {
    @GetMapping("/application")
    fun edit():String {
        return "application/edit"
    }
}