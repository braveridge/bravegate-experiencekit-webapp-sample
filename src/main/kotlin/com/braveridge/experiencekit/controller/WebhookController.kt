package com.braveridge.experiencekit.controller

import com.braveridge.experiencekit.form.WebhookForm
import com.braveridge.experiencekit.properties.BravegateProperties
import com.braveridge.experiencekit.service.*
import com.braveridge.experiencekit.spec.SensorType
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.lang.RuntimeException
import javax.servlet.http.HttpServletRequest


@RestController
@RequestMapping("webhook")
class WebhookController(
    private val bvgService: BravegateService,
    private val sensorlogService: SensorLogService,
    private val luxService: LuxService,
    private val moveDetectionService: MoveDetectionService,
    private val temperatureService: TemperatureService,
    private val humidityService: HumidityService,
    private val mapper: ObjectMapper
) {

    @PostMapping("sensor")
    fun post(
        @RequestBody jsonString: String,
        request: HttpServletRequest
    ): String {

        // check token
        if(!bvgService.checkAppToken(jsonString, request)){
            throw RuntimeException()
        }

        // json -> form
        val form = mapper.readValue(jsonString, WebhookForm::class.java)

        // form -> DB
        sensorlogService.create(form, jsonString)
        when (SensorType.of(form.device.sensorId)) {
            SensorType.Lux -> luxService.create(form)
            SensorType.MoveDetection -> moveDetectionService.create(form)
            SensorType.Temperature -> temperatureService.create(form)
            SensorType.Humidity -> humidityService.create(form)
            else -> return "UNKNOWN_SENSOR"
        }

        return "OK"
    }
}