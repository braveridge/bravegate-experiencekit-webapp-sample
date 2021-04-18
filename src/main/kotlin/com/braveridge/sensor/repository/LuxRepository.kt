package com.braveridge.sensor.repository

import com.braveridge.sensor.db.entity.LuxEntity
import com.braveridge.sensor.db.mapper.LuxMapper
import com.braveridge.sensor.repository.`interface`.SensorDataRepositoryInterface
import org.apache.ibatis.jdbc.RuntimeSqlException
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
class LuxRepository(private val mapper: LuxMapper) : SensorDataRepositoryInterface, LuxMapper by mapper
{
    fun create(entity: LuxEntity): LuxEntity{
        mapper.insert(entity)
        return mapper.findById(entity.id) ?: throw RuntimeSqlException()
    }
    // 末尾データ（最新データ）取得
    override fun getLast(deviceId: String) = mapper.lastSensorEntityByDeviceId(deviceId)
    // 平均データ取得
    override fun getAve(deviceId: String, sensorId: String, afterOrEqualDate: LocalDateTime) = mapper.getAverageValue(deviceId, sensorId, afterOrEqualDate)
}