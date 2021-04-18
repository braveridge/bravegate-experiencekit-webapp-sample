package com.braveridge.sensor.repository.`interface`

import com.braveridge.sensor.db.entity.SensorDataEntity
import com.braveridge.sensor.db.entity.`interface`.SensorDataEntityInterface
import java.time.LocalDateTime

// センサデータ利用インターフェース
interface SensorDataRepositoryInterface {
    // 最大値取得
    //fun getMax(deviceId: String, sensorId: String, afterOrEqualDate: LocalDateTime): Double
    // 最小値取得
    //fun getMin(deviceId: String, sensorId: String, afterOrEqualDate: LocalDateTime): Double
    // 平均値取得
    fun getAve(deviceId: String, sensorId: String, afterOrEqualDate: LocalDateTime): Double
    // 末尾データ取得
    fun getLast(deviceId: String): SensorDataEntityInterface?
}