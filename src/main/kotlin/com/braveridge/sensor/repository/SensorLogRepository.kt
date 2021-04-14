package com.braveridge.sensor.repository

import com.braveridge.sensor.db.entity.SensorLogEntity
import com.braveridge.sensor.db.mapper.SensorLogMapper
import org.apache.ibatis.jdbc.RuntimeSqlException
import org.springframework.stereotype.Repository

@Repository
class SensorLogRepository(private val mapper: SensorLogMapper) : SensorLogMapper by mapper {

    fun create(entity: SensorLogEntity): SensorLogEntity {
        mapper.insert(entity)
        return mapper.findById(entity.id) ?: throw RuntimeSqlException()
    }
}