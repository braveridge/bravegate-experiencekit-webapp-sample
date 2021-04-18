package com.braveridge.sensor.session

import com.braveridge.sensor.spec.DataSpan
import com.braveridge.sensor.spec.SensorType
import org.springframework.stereotype.Component
import org.springframework.web.context.annotation.SessionScope
import java.io.Serializable

@Component
@SessionScope
data class ChartSession(var lastDeviceId: String? = null,
                        val deviceIdMap: MutableMap<SensorType, String> = mutableMapOf(),
                        var lastDataSpan: DataSpan? = null) : Serializable {
    val dataSpan: DataSpan
        get() = lastDataSpan ?: DataSpan.Hour

    fun deviceId(sensorType: SensorType) = deviceIdMap[sensorType] ?: lastDeviceId

    fun store(sensorId: String,
              deviceId: String,
              dataSpan: DataSpan? = null) {
        this.lastDeviceId = deviceId
        val sensorType = SensorType.of(sensorId)
        if (sensorType != SensorType.Unknown) {
            deviceIdMap[sensorType] = deviceId
        }

        dataSpan?.let {
            this.lastDataSpan = it
        }
    }
}