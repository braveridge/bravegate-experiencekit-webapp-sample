package com.braveridge.sensor.service

import com.braveridge.sensor.db.entity.LuxEntity
import com.braveridge.sensor.db.entity.SensorDataEntity
import com.braveridge.sensor.form.WebhookForm
import com.braveridge.sensor.repository.LuxRepository
import com.braveridge.sensor.spec.DataSpan
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
@Transactional
class LuxService(private val repository: LuxRepository) {

    fun create(form: WebhookForm): LuxEntity {
        val entity = convertToEntity(form)
        return repository.create(entity)
    }

    fun convertToEntity(form: WebhookForm) = LuxEntity(
        0, form.router.routerId, form.device.deviceId, form.device.rssi, form.device.sensorId,
        form.device.dataAsDouble("lux"), form.uplinkId, form.date.toLocalDateTime()
    )

    fun listByDeviceAndSensor(deviceId: String, sensorId: String, dataSpan: DataSpan, wantsAllData: Boolean = false): List<SensorDataEntity> {
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
                .map { SensorDataEntity(it.deviceId, it.date, it.value, it.deviceRssi) }
    }
}