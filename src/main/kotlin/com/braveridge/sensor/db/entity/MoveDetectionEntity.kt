package com.braveridge.sensor.db.entity

import com.braveridge.sensor.db.entity.`interface`.SensorDataEntityInterface
import java.time.LocalDateTime

data class MoveDetectionEntity(
    val id: Long,
    val routerId: String,
    val deviceId: String,
    val deviceRssi: String,
    val sensorId: String,
    val isDetected: Boolean,
    val count: Int,
    val uplinkId: String,
    val date: LocalDateTime,
    val createdAt: LocalDateTime
) : SensorDataEntityInterface {
    override fun sensorDeviceId() = deviceId
    override fun sensorId() = sensorId
    override fun sensorValue() = count.toDouble()
    override fun sensorDate() = date
}
