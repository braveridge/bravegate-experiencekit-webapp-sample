package com.braveridge.sensor.repository

import com.braveridge.sensor.db.entity.TemperatureEntity
import com.braveridge.sensor.db.mapper.TemperatureMapper
import com.braveridge.sensor.repository.`interface`.SensorDataRepositoryInterface
import org.apache.ibatis.jdbc.RuntimeSqlException
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
class TemperatureRepository(private val mapper: TemperatureMapper) : SensorDataRepositoryInterface, TemperatureMapper by mapper
{
    fun Create(entity: TemperatureEntity): TemperatureEntity{
        mapper.insert(entity)
        return mapper.findById(entity.id) ?: throw RuntimeSqlException()
    }
    // 末尾データ（最新データ）取得
    override fun GetLast(deviceId: String) = mapper.lastSensorEntityByDeviceId(deviceId)
    // 平均データ取得
    override fun GetAve(deviceId: String, sensorId: String, afterOrEqualDate: LocalDateTime) = mapper.getAverageValue(deviceId, sensorId, afterOrEqualDate)
}