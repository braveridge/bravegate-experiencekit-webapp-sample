package com.braveridge.sensor.db.entity

import com.braveridge.sensor.db.entity.`interface`.SensorDataEntityInterface
import java.time.LocalDateTime

data class MoveDetectionEntity(
    val id: Long,
    val routerId: String,
    val deviceId: String,
    val deviceRssi:  Int,
    val sensorId: String,
    val isDetected: Boolean,
    val count: Long,
    val uplinkId: String,
    val date: LocalDateTime,
    val createdAt: LocalDateTime? = null
) : SensorDataEntityInterface {
    override fun sensorDeviceId() = deviceId
    override fun sensorId() = sensorId
    override fun sensorValue() = count.toDouble()
    override fun sensorDate() = date
}
