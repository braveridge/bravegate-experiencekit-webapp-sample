package com.braveridge.sensor.controller

import com.braveridge.sensor.db.entity.SensorLogEntity
import com.braveridge.sensor.spec.SensorType
import com.braveridge.sensor.properties.BravegateProperties
import com.braveridge.sensor.service.BravegateService
import com.braveridge.sensor.service.SimplePager
import com.braveridge.sensor.service.SensorLogService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

@Controller
@RequestMapping("/sensorlog")
class SensorLogController (private val sensorLogService: SensorLogService,
                           private val bvgService: BravegateService,
                           private val properties: BravegateProperties){
    companion object {
        const val COUNT_PER_PAGE = 50
    }

    // 一覧
    @GetMapping("/list")
    fun list(@RequestParam("page", required = false, defaultValue = "1") page: Int,
             @RequestParam("app_id", required = false) appId: String?,
             @RequestParam("router_id", required = false) routerId: String?,
             @RequestParam("device_id", required = false) deviceId: String?,
             @RequestParam("sensor_id", required = false) sensorId: String?,
             model: Model
    ): String {

        val pager = SimplePager(page, COUNT_PER_PAGE)
        val logList = sensorLogService.findAll(pager, appId, routerId, deviceId, sensorId)
            .map { WebhookLogResponse.of(it) }

        model.addAttribute("logList", logList)
        model.addAttribute("pager", pager)

        val appList = bvgService.getApplicationList(properties.authkeyId, properties.authkeySecret)
        val routerList = bvgService.getRouterList(properties.authkeyId, properties.authkeySecret)
        val deviceList = bvgService.getDeviceList(properties.authkeyId, properties.authkeySecret)
        val sensorList = SensorType.values()
            .flatMap { sensor -> sensor.ids.map { id -> SimpleSensor(id, sensor.name) } }
            .toList()
        if(appList.isNotEmpty()) {
            model.addAttribute("applicationId", appList[0].applicationId)
        }
        if(routerList.isNotEmpty()){
            model.addAttribute("routerId", routerList[0].routerId)
        }
        model.addAttribute("deviceList", deviceList)
        model.addAttribute("sensorList", sensorList)

        model.addAttribute("queryAppId", appId ?: "")
        model.addAttribute("queryRouterId", routerId ?: "")
        model.addAttribute("queryDeviceId", deviceId ?: "")
        model.addAttribute("querySensorId", sensorId ?: "")
        return "sensorlog/list"
    }
    data class SimpleSensor(val id: String, val name: String)
    data class WebhookLogResponse(val id: Long,
                                  val applicationId: String,
                                  val applicationName: String,
                                  val routerId: String,
                                  val routerImsi: String,
                                  val routerRssi: Int,
                                  val routerBattery: Int,
                                  val routerFwVersion: String,
                                  val deviceId: String,
                                  val sensorId: String,
                                  val sensorName: String,
                                  val deviceRssi: Int,
                                  val data: String,
                                  val uplinkId: String,
                                  val date: LocalDateTime,
                                  val jsonString: String?,
                                  val createdAt: LocalDateTime?,
                                  val isLate: Boolean) {
        companion object {
            private const val lateMinute = 5

            fun of(s: SensorLogEntity): WebhookLogResponse {
                val isLate = s.createdAt?.let { createdAt ->
                    ChronoUnit.MINUTES.between(s.date, createdAt) >= lateMinute
                } ?: false
                return WebhookLogResponse(
                    s.id, s.applicationId, s.applicationName,
                    s.routerId, s.routerImsi, s.routerRssi, s.routerBattery, s.routerFwVersion,
                    s.deviceId, s.sensorId, s.sensorName, s.deviceRssi, s.data,
                    s.uplinkId, s.date, s.jsonString, s.createdAt, isLate)
            }
        }
    }
}