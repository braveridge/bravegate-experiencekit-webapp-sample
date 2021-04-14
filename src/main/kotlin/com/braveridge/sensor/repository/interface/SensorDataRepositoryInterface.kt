package com.braveridge.sensor.repository.`interface`

import com.braveridge.sensor.db.entity.`interface`.SensorDataEntityInterface
import java.time.LocalDateTime

// センサデータ利用インターフェース
interface SensorDataRepositoryInterface {
    // 最大値取得
    //fun GetMax(deviceId: String, sensorId: String, afterOrEqualDate: LocalDateTime): Double
    // 最小値取得
    //fun GetMin(deviceId: String, sensorId: String, afterOrEqualDate: LocalDateTime): Double
    // 平均値取得
    fun GetAve(deviceId: String, sensorId: String, afterOrEqualDate: LocalDateTime): Double
    // 末尾データ取得
    fun GetLast(deviceId: String): SensorDataEntityInterface?
}