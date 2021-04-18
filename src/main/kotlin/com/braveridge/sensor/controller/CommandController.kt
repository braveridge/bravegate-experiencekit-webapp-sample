package com.braveridge.sensor.controller

import com.braveridge.partnerconference.form.ifc.CommandForm
import com.braveridge.sensor.client.request.CommandRequest
import com.braveridge.sensor.properties.BravegateProperties
import com.braveridge.sensor.service.BravegateService
import com.braveridge.sensor.service.SimplePager
import org.springframework.boot.Banner
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@Controller
@RequestMapping("/command/")
class CommandController(private val service: BravegateService,
                        private val properties: BravegateProperties) {
    companion object {
        const val COUNT_PER_PAGE = 50
    }

    @ResponseBody
    @PostMapping("send")
    fun sendCommand(@RequestBody @Validated form: CommandForm): PostSendResponse {
        val request = CommandRequest(form.name, form.params, form.targets)
        val commandId = service.sendCommand(properties.authkeyId, properties.authkeySecret, request) ?: throw RuntimeException()
        return PostSendResponse(commandId)
    }
    data class PostSendResponse(val commandId: String)

    @GetMapping("list")
    fun getCommandList(@RequestParam("page", required = false, defaultValue = "1") page: Int,
                       model: Model): String {
        val pager = SimplePager(page, COUNT_PER_PAGE)
        model.addAttribute("pager", pager)
        val commandList = service.getCommandList(properties.authkeyId, properties.authkeySecret)
        model.addAttribute("commandList", commandList)
        return "command/list"
    }
}