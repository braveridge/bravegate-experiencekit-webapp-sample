package com.braveridge.experiencekit.spec

import java.io.Serializable

enum class SensorType(
    val ids: List<String>,
    val description: String,
    val valueColumn: String? = null,
    val flagColumn: String? = null
) : Serializable {
    Temperature(listOf("0004"), "温度センサー", valueColumn = "temperature"),
    Humidity(listOf("0005"), "湿度センサー", valueColumn = "humidity"),
    Lux(listOf("0007"), "照度センサー", valueColumn = "lux"),
    MoveDetection(listOf("0015"), "振動検知", valueColumn = "move_info_count", flagColumn = "move_info_flag"),
    Unknown(listOf(), "不明");

    companion object {
        fun of(id: String): SensorType {
            return values().firstOrNull { it.ids.contains(id) } ?: Unknown
        }

        fun notifyTypes() = listOf(Temperature, Humidity, Lux, MoveDetection)
    }

    fun contains(sensorId: String) = ids.contains(sensorId)
}