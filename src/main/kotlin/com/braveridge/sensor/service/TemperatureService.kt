package com.braveridge.sensor.service

import com.braveridge.sensor.db.entity.SensorDataEntity
import com.braveridge.sensor.db.entity.TemperatureEntity
import com.braveridge.sensor.form.WebhookForm
import com.braveridge.sensor.repository.TemperatureRepository
import com.braveridge.sensor.spec.DataSpan
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
@Transactional
class TemperatureService(private val repository: TemperatureRepository) {
    fun create(form: WebhookForm): TemperatureEntity {
        val entity = convertToEntity(form)
        return repository.Create(entity)
    }

     fun convertToEntity(form: WebhookForm) = TemperatureEntity(
        0,
         form.router.routerId,
         form.device.deviceId,
         form.device.rssi,
         form.device.sensorId,
         form.device.dataAsDouble("temperature"),
         form.uplinkId,
         form.date.toLocalDateTime(),
         null)

    fun listByDeviceAndSensor(deviceId: String, sensorId: String, dataSpan: DataSpan, wantsAllData: Boolean = false): List<SensorDataEntity> {
        val now = LocalDateTime.now()
        val afterOrEqualDate = when(dataSpan) {
            DataSpan.Hour -> now.minusHours(1)
            DataSpan.Day -> now.minusDays(1)
            DataSpan.Week -> now.minusWeeks(1)
            DataSpan.Month -> now.minusMonths(1)
            DataSpan.Days3 -> now.minusDays(3)
            else -> now
        }
        return if (!wantsAllData && (dataSpan == DataSpan.Week || dataSpan == DataSpan.Month))
            repository.findSensorData(deviceId, sensorId, afterOrEqualDate) else
            repository.findAll(deviceId, sensorId, afterOrEqualDate)
                .map { SensorDataEntity(it.deviceId, it.date, it.value, it.deviceRssi) }
    }
}