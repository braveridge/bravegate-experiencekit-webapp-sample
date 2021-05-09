package com.braveridge.experiencekit.controller

import com.braveridge.experiencekit.spec.SensorType
import com.braveridge.experiencekit.properties.BravegateProperties
import com.braveridge.experiencekit.service.*
import com.braveridge.experiencekit.session.ChartSession
import com.braveridge.experiencekit.spec.DataSpan
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime


@Controller
@RequestMapping("/chart/")
class ChartController(
    private val luxService: LuxService,
    private val moveDetectionService: MoveDetectionService,
    private val temperatureService: TemperatureService,
    private val humidityService: HumidityService,
    private val bvgService: BravegateService,
    private val chartSession: ChartSession
) {

    // グラフ表示(照度センサー)
    @GetMapping("lux")
    fun lux(@RequestParam("device_id") deviceId: String?, model: Model): String {
        val deviceList = getDeviceListBySensorType(SensorType.Lux)
        model.addAttribute("deviceList", deviceList)
        model.addAttribute("sensorName", "照度センサー")
        model.addAttribute("legend", "照度センサー(lux)")
        model.addAttribute("dataEndpoint", "lux-data")
        model.addAttribute("defaultDateSpan", chartSession.dataSpan.rawValue)
        model.addAttribute("queryDeviceId", deviceId ?: chartSession.deviceId(SensorType.Lux))
        return "chart/sensor"
    }

    // データ取得(照度センサー)
    @ResponseBody
    @GetMapping("lux-data")
    fun luxData(
        @RequestParam("device_id") deviceId: String,
        @RequestParam("sensor_id") sensorId: String,
        @RequestParam("span") span: Int
    ): List<DataResponse> {
        val dataSpan = DataSpan.of(span)
        chartSession.store(sensorId, deviceId, dataSpan)
        return luxService
            .listByDeviceAndSensor(deviceId, sensorId, dataSpan)
            .map { DataResponse(it.date, it.data, it.deviceRssi) }
    }


    // グラフ表示(温度センサー)
    @GetMapping("temperature")
    fun temperature(@RequestParam("device_id") deviceId: String?, model: Model): String {
        val deviceList =
            getDeviceListBySensorType(SensorType.Temperature)
        model.addAttribute("deviceList", deviceList)
        model.addAttribute("sensorName", "温度センサー")
        model.addAttribute("legend", "温度センサー(℃)")
        model.addAttribute("dataEndpoint", "temperature-data")
        model.addAttribute("defaultDateSpan", chartSession.dataSpan.rawValue)
        model.addAttribute("queryDeviceId", deviceId ?: chartSession.deviceId(SensorType.Temperature))
        return "chart/sensor"
    }

    // データ取得(温度センサー)
    @ResponseBody
    @GetMapping("temperature-data")
    fun temperatureData(
        @RequestParam("device_id") deviceId: String,
        @RequestParam("sensor_id") sensorId: String,
        @RequestParam("span") span: Int
    ): List<DataResponse> {
        val dataSpan = DataSpan.of(span)
        chartSession.store(sensorId, deviceId, dataSpan)
        return temperatureService
            .listByDeviceAndSensor(deviceId, sensorId, dataSpan)
            .map { DataResponse(it.date, it.data, it.deviceRssi) }
    }


    // グラフ表示(振動検知)
    @GetMapping("move-detection")
    fun moveDetection(@RequestParam("device_id") deviceId: String?, model: Model): String {
        val deviceList =
            getDeviceListBySensorType(SensorType.MoveDetection)
        model.addAttribute("deviceList", deviceList)
        model.addAttribute("sensorName", "振動センサー")
        model.addAttribute("legend", "振動センサー")
        model.addAttribute("dataEndpoint", "move-detection-data")
        model.addAttribute("defaultDateSpan", chartSession.dataSpan.rawValue)
        model.addAttribute("queryDeviceId", deviceId ?: chartSession.deviceId(SensorType.MoveDetection))
        return "chart/move_detection"
    }

    // データ取得(振動検知)
    @ResponseBody
    @GetMapping("move-detection-data")
    fun moveDetectionData(
        @RequestParam("device_id") deviceId: String,
        @RequestParam("sensor_id") sensorId: String,
        @RequestParam("span") span: Int
    ): List<DataResponse> {
        val dataSpan = DataSpan.of(span)
        chartSession.store(sensorId, deviceId, dataSpan)
        return moveDetectionService
            .listByDeviceAndSensor(deviceId, sensorId, dataSpan)
            .map { DataResponse(it.date, it.data, it.deviceRssi) }
    }


    // グラフ表示(湿度センサー)
    @GetMapping("humidity")
    fun humidity(
        @RequestParam("device_id") deviceId: String?,
        model: Model
    ): String {
        val deviceList = getDeviceListBySensorType(SensorType.Humidity)
        model.addAttribute("deviceList", deviceList)
        model.addAttribute("sensorName", "湿度センサー")
        model.addAttribute("legend", "湿度センサー(%)")
        model.addAttribute("dataEndpoint", "humidity-data")
        model.addAttribute("defaultDateSpan", chartSession.dataSpan.rawValue)
        model.addAttribute("queryDeviceId", deviceId ?: chartSession.deviceId(SensorType.Humidity))
        return "chart/sensor"
    }

    // データ取得(湿度センサー)
    @ResponseBody
    @GetMapping("humidity-data")
    fun humidityData(
        @RequestParam("device_id") deviceId: String,
        @RequestParam("sensor_id") sensorId: String,
        @RequestParam("span") span: Int
    ): List<DataResponse> {
        val dataSpan = DataSpan.of(span)
        chartSession.store(sensorId, deviceId, dataSpan)
        return humidityService
            .listByDeviceAndSensor(deviceId, sensorId, dataSpan)
            .map { DataResponse(it.date, it.data, it.deviceRssi) }
    }

    data class DataResponse(
        val date: LocalDateTime,
        val data: Double,
        val deviceRssi: Int
    )

    data class SimpleDevice(
        val id: String,
        val name: String,
        val sensorId: String = "",
        val sensorName: String? = null
    ) {
        val modelId: String
            get() = id.substring(6, 10)
    }

    fun getDeviceListBySensorType(sensorType: SensorType) =
        bvgService.getDeviceList()
            .map { d -> d.sensors.map { SimpleDevice(d.deviceId, d.name, it.sensorId) } }
            .flatten()
            .filter { sensorType.contains(it.sensorId) }
            .sortedBy { it.id }
}