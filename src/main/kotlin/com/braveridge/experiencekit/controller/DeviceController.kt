package com.braveridge.experiencekit.controller

import com.braveridge.experiencekit.form.DeviceCreateForm
import com.braveridge.experiencekit.client.request.DeviceCreateRequest
import com.braveridge.experiencekit.client.request.DeviceUpdateRequest
import com.braveridge.experiencekit.form.DeviceUpdateForm
import com.braveridge.experiencekit.properties.BravegateProperties
import com.braveridge.experiencekit.service.BravegateService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import java.lang.RuntimeException

@Controller
@RequestMapping("/device")
class DeviceController(
    private val service: BravegateService) {

    // 一覧
    @GetMapping("")
    fun list(model: Model): String {
        val deviceList = service.getDeviceList()
        model.addAttribute("deviceList", deviceList)
        return "device/list"
    }

    // 更新
    @GetMapping("/edit/{deviceId}")
    fun edit(
        model: Model,
        @PathVariable("deviceId") deviceId: String
    ): String {
        val device = service.getDevice(deviceId)
        model.addAttribute("device", device)
        return "device/edit"
    }

    @PostMapping("/edit/{deviceId}")
    @ResponseBody
    fun update(
        @PathVariable("deviceId") deviceId: String,
        @RequestBody @Validated form: DeviceUpdateForm
    ): BravegateService.Device? {
        val request = DeviceUpdateRequest(form.groupId, form.name)
        return service.updateDevice(deviceId, request)
            ?: throw RuntimeException()
    }

    // 登録
    @GetMapping("/create")
    fun create(model: Model): String {
        return "device/create"
    }

    @PostMapping("/create")
    @ResponseBody
    fun create(@RequestBody @Validated form: DeviceCreateForm): BravegateService.Device? {
        val request = DeviceCreateRequest("", form.deviceId, form.registrationCode, form.name)
        return service.createDevice(request)
    }

    // 削除
    @PostMapping("/delete/{deviceId}")
    @ResponseBody
    fun delete(@PathVariable("deviceId") deviceId: String): Boolean {
        return service.deleteDevice(deviceId)
    }
}