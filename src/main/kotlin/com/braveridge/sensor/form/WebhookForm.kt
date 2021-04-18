package com.braveridge.sensor.form

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonProperty
import java.time.ZonedDateTime
import javax.validation.Valid
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

class WebhookForm {
    @NotNull
    @Valid
    lateinit var application: Application
    @NotNull
    @Valid
    lateinit var router: Router
    @NotNull
    @Valid
    lateinit var device: Device
    @NotBlank
    @JsonProperty("uplink_id")
    lateinit var uplinkId: String
    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssxxxxx")
    lateinit var date: ZonedDateTime

    class Application {
        @NotBlank
        @JsonProperty("application_id")
        lateinit var applicationId: String
        @NotBlank
        lateinit var name: String
    }

    class Router {
        @NotBlank
        @JsonProperty("router_id")
        lateinit var routerId: String
        @NotBlank
        lateinit var imsi: String
        var rssi: Int = 0
        var battery: Int = 0
        @NotBlank
        @JsonProperty("fw_version")
        lateinit var fwVersion: String
    }

    class Device {
        @NotBlank
        @JsonProperty("device_id")
        lateinit var deviceId: String
        @NotBlank
        @JsonProperty("sensor_id")
        lateinit var sensorId: String
        @NotBlank
        @JsonProperty("sensor_name")
        lateinit var sensorName: String
        var rssi: Int = 0
        lateinit var data: Map<String, Any>

        fun dataAsDouble(key: String) = data[key] as? Double ?: (data[key] as Int).toDouble()
        fun dataAsLong(key: String) = data[key] as? Long ?: (data[key] as Int).toLong()
        fun dataAsInt(key: String) = data[key] as? Int

        fun copyWith(deviceId: String? = null, sensorId: String? = null, sensorName: String? = null, rssi: Int? = null, data: Map<String, Any>? = null): Device {
            val device = Device()
            device.deviceId = deviceId ?: this.deviceId
            device.sensorId = sensorId ?: this.sensorId
            device.sensorName = sensorName ?: this.sensorName
            device.rssi = rssi ?: this.rssi
            device.data = data ?: this.data
            return device
        }
    }

    fun copyWith(application: Application? = null, router: Router? = null, device: Device? = null, uplinkId: String? = null, date: ZonedDateTime? = null): WebhookForm {
        val webhookForm = WebhookForm()
        webhookForm.application = application ?: this.application
        webhookForm.router = router ?: this.router
        webhookForm.device = device ?: this.device
        webhookForm.uplinkId = uplinkId ?: this.uplinkId
        webhookForm.date = date ?: this.date
        return webhookForm
    }

    override fun toString(): String {
        return "applicationId=${application.applicationId}, applicationName=${application.name}. " +
                "routerId=${router.routerId}, routerImsi=${router.imsi}, routerRssi=${router.rssi}, routerBattery=${router.battery}, routerFwVersion=${router.fwVersion}. " +
                "deviceId=${device.deviceId}, sensorId=${device.sensorId}, sensorName=${device.sensorName}, deviceRssi=${device.rssi}, deviceData=${device.data}. " +
                "uplinkId=$uplinkId, date=${date}"
    }
}