package com.braveridge.experiencekit.repository

import com.braveridge.experiencekit.db.entity.SensorLogEntity
import com.braveridge.experiencekit.db.mapper.SensorLogMapper
import org.apache.ibatis.jdbc.RuntimeSqlException
import org.springframework.stereotype.Repository

@Repository
class SensorLogRepository(private val mapper: SensorLogMapper) : SensorLogMapper by mapper {

    fun create(entity: SensorLogEntity): SensorLogEntity {
        mapper.insert(entity)
        return mapper.findById(entity.id) ?: throw RuntimeSqlException()
    }
}