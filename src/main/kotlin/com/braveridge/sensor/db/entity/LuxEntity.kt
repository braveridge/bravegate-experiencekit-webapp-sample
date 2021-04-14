package com.braveridge.sensor.db.entity

import com.braveridge.sensor.db.entity.`interface`.SensorDataEntityInterface
import java.time.LocalDateTime

data class LuxEntity(
    val id: Long,
    val routerId: String,
    val deviceId: String,
    val deviceRssi: Int,
    val sensorId: String,
    val value: Double,
    val uplinkId: String,
    val date: LocalDateTime,
    val createdAt: LocalDateTime
) : SensorDataEntityInterface {
    override fun sensorDeviceId() = deviceId
    override fun sensorId() = sensorId
    override fun sensorValue() = value
    override fun sensorDate() = date
}
