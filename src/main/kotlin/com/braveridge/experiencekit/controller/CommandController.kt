package com.braveridge.experiencekit.controller

import com.braveridge.partnerconference.form.ifc.CommandForm
import com.braveridge.experiencekit.client.request.CommandRequest
import com.braveridge.experiencekit.properties.BravegateProperties
import com.braveridge.experiencekit.service.BravegateService
import com.braveridge.experiencekit.service.SimplePager
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@Controller
@RequestMapping("/command/")
class CommandController(
    private val service: BravegateService) {
    companion object {
        const val COUNT_PER_PAGE = 50
    }

    @ResponseBody
    @PostMapping("send")
    fun sendCommand(@RequestBody @Validated form: CommandForm): PostSendResponse {
        val request = CommandRequest(form.name, form.params, form.targets)
        val commandId =
            service.sendCommand(request) ?: throw RuntimeException()
        return PostSendResponse(commandId)
    }

    data class PostSendResponse(val commandId: String)

    @GetMapping("list")
    fun getCommandList(
        @RequestParam("page", required = false, defaultValue = "1") page: Int,
        model: Model
    ): String {
        val pager = SimplePager(page, COUNT_PER_PAGE)
        model.addAttribute("pager", pager)
        val commandList = service.getCommandList()
        model.addAttribute("commandList", commandList)
        return "command/list"
    }
}