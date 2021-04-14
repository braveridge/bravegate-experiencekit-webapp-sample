package com.braveridge.sensor.repository

import com.braveridge.sensor.db.entity.MoveDetectionEntity
import com.braveridge.sensor.db.mapper.MoveDetectionMapper
import com.braveridge.sensor.repository.`interface`.SensorDataRepositoryInterface
import org.apache.ibatis.jdbc.RuntimeSqlException
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
class MoveDetectionRepository(private val mapper: MoveDetectionMapper) : SensorDataRepositoryInterface, MoveDetectionMapper by mapper
{
    fun Create(entity: MoveDetectionEntity): MoveDetectionEntity{
        mapper.insert(entity)
        return mapper.findById(entity.id) ?: throw RuntimeSqlException()
    }
    // 末尾データ（最新データ）取得
    override fun GetLast(deviceId: String) = mapper.lastSensorEntityByDeviceId(deviceId)
    // 平均データ取得
    override fun GetAve(deviceId: String, sensorId: String, afterOrEqualDate: LocalDateTime) = mapper.getAverageValue(deviceId, sensorId, afterOrEqualDate)
}