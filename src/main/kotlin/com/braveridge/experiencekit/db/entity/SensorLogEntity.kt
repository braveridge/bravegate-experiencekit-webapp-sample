package com.braveridge.experiencekit.db.entity

import java.time.LocalDateTime

data class SensorLogEntity(
    val id: Long,
    val applicationId: String,
    val applicationName: String,
    val routerId: String,
    val routerImsi: String,
    val routerRssi: Int,
    val routerBattery: Int,
    val routerFwVersion: String,
    val deviceId: String,
    val sensorId: String,
    val sensorName: String,
    val deviceRssi: Int,
    val data: String,
    val uplinkId: String,
    val date: LocalDateTime,
    val jsonString: String? = null,
    val createdAt: LocalDateTime? = null
)