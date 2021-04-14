package com.braveridge.sensor.repository

import com.braveridge.sensor.db.entity.HumidityEntity
import com.braveridge.sensor.db.mapper.HumidityMapper
import com.braveridge.sensor.repository.`interface`.SensorDataRepositoryInterface
import org.apache.ibatis.jdbc.RuntimeSqlException
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
class HumidityRepository(private val mapper: HumidityMapper) : SensorDataRepositoryInterface, HumidityMapper by mapper
{
    fun Create(entity: HumidityEntity): HumidityEntity{
        mapper.insert(entity)
        return mapper.findById(entity.id) ?: throw RuntimeSqlException()
    }
    // 末尾データ（最新データ）取得
    override fun GetLast(deviceId: String) = mapper.lastSensorEntityByDeviceId(deviceId)
    // 平均データ取得
    override fun GetAve(deviceId: String, sensorId: String, afterOrEqualDate: LocalDateTime) = mapper.getAverageValue(deviceId, sensorId, afterOrEqualDate)
}