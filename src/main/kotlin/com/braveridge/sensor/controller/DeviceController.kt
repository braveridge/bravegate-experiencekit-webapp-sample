package com.braveridge.sensor.controller

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping

@Controller
class DeviceController() {
    @GetMapping("/device")
    fun edit(model: Model):String {
        return "device/edit"
    }
}