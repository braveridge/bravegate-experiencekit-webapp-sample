package com.braveridge.experiencekit.db.entity

import java.time.LocalDateTime

data class SensorDataEntity(
    val deviceId: String,
    val date: LocalDateTime,
    val data: Double,
    val deviceRssi: Int
)