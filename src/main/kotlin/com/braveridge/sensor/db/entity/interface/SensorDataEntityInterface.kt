package com.braveridge.sensor.db.entity.`interface`

import java.time.LocalDateTime

interface SensorDataEntityInterface {
    fun sensorDeviceId(): String
    fun sensorId(): String
    fun sensorValue(): Double
    fun sensorDate(): LocalDateTime
}