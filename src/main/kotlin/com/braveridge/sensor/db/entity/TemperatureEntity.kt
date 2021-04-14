package com.braveridge.sensor.db.entity

import com.braveridge.sensor.db.entity.`interface`.SensorDataEntityInterface
import java.time.LocalDateTime

data class TemperatureEntity(
    val id: Long,
    val routerId: String,
    val deviceId: String,
    val deviceRssi: Int,
    val sensorId: String,
    val sensorName: String,
    val value: Double,
    val uplinkId: String,
    val date: LocalDateTime,
    val createAt: LocalDateTime
) : SensorDataEntityInterface {
    override fun sensorDeviceId() = deviceId
    override fun sensorId() = sensorId
    override fun sensorValue() = value
    override fun sensorDate() = date
}