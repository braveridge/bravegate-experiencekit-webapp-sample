package com.braveridge.experiencekit.service

import com.braveridge.experiencekit.db.entity.MoveDetectionEntity
import com.braveridge.experiencekit.db.entity.SensorDataEntity
import com.braveridge.experiencekit.form.WebhookForm
import com.braveridge.experiencekit.repository.MoveDetectionRepository
import com.braveridge.experiencekit.spec.DataSpan
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
@Transactional
class MoveDetectionService(private val repository: MoveDetectionRepository) {
    fun create(form: WebhookForm): MoveDetectionEntity {
        val entity = convertToEntity(form)
        return repository.create(entity)
    }

    fun convertToEntity(form: WebhookForm) = MoveDetectionEntity(
        0, form.router.routerId, form.device.deviceId, form.device.rssi, form.device.sensorId,
        (form.device.data["move_info_flag"] as Int).toBoolean(), (form.device.data["move_info_count"] as Int).toLong(),
        form.uplinkId, form.date.toLocalDateTime()
    )

    fun listByDeviceAndSensor(
        deviceId: String,
        sensorId: String,
        dataSpan: DataSpan,
        wantsAllData: Boolean = false
    ): List<SensorDataEntity> {
        val now = LocalDateTime.now()
        val afterOrEqualDate = when (dataSpan) {
            DataSpan.Hour -> now.minusHours(1)
            DataSpan.Day -> now.minusDays(1)
            DataSpan.Week -> now.minusWeeks(1)
            DataSpan.Month -> now.minusMonths(1)
            else -> now
        }
        return if (!wantsAllData && (dataSpan == DataSpan.Week || dataSpan == DataSpan.Month))
            repository.findSensorData(deviceId, sensorId, afterOrEqualDate) else
            repository.findAll(deviceId, sensorId, afterOrEqualDate)
                .map { SensorDataEntity(it.deviceId, it.date, if (it.isDetected) 1.0 else 0.0, it.deviceRssi) }
    }

    fun listCountByDeviceAndSensor(
        deviceId: String,
        sensorId: String,
        dataSpan: DataSpan,
        wantsAllData: Boolean = false
    ): List<SensorDataEntity> {
        val now = LocalDateTime.now()
        val afterOrEqualDate = when (dataSpan) {
            DataSpan.Hour -> now.minusHours(1)
            DataSpan.Day -> now.minusDays(1)
            DataSpan.Week -> now.minusWeeks(1)
            else -> now
        }
        return if (!wantsAllData && dataSpan == DataSpan.Week)
            repository.findSensorCountData(deviceId, sensorId, afterOrEqualDate) else
            repository.findAll(deviceId, sensorId, afterOrEqualDate)
                .map { SensorDataEntity(it.deviceId, it.date, it.count.toDouble(), it.deviceRssi) }
    }

    private fun Int.toBoolean() = (this != 0)
}