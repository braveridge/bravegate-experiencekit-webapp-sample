package com.braveridge.sensor.controller

import com.braveridge.sensor.client.request.RouterCreateRequest
import com.braveridge.sensor.form.RouterCreateForm
import com.braveridge.sensor.properties.BravegateProperties
import com.braveridge.sensor.service.BravegateService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import java.lang.RuntimeException
import java.time.LocalDateTime

@Controller
@RequestMapping("/router")
class RouterController(private val service: BravegateService,
                       private val properties: BravegateProperties) {
    // 一覧
    @GetMapping("")
    fun list(model: Model):String {
        val routerList = service.getRouterList(properties.authkeyId, properties.authkeySecret)
        if(routerList.isNotEmpty()){
            model.addAttribute("router", routerList[0])
        }
        else{
            val router = BravegateService.Router("", "", "", 0, 0, "", "", LocalDateTime.MAX, LocalDateTime.MAX)
            model.addAttribute("router", router)
        }
        return "router/edit"
    }

    // 登録
    @GetMapping("/create")
    fun create():String {
        return "router/create"
    }

    @PostMapping("/create")
    @ResponseBody
    fun create(@RequestBody @Validated form: RouterCreateForm): BravegateService.Router {
        val request = RouterCreateRequest("", form.routerId, form.registrationCode, form.name )
        return service.createRouter(properties.authkeyId, properties.authkeySecret, request)
            ?: throw RuntimeException()
    }

    // 削除
    @PostMapping("/delete/{routerId}")
    @ResponseBody
    fun delete(@PathVariable("routerId") routerId: String ): Boolean {
        return service.deleteRouter(properties.authkeyId, properties.authkeySecret, routerId)
    }
}