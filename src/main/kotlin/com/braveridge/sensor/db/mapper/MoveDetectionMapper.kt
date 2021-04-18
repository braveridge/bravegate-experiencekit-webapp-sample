package com.braveridge.sensor.db.mapper

import org.apache.ibatis.annotations.*
import com.braveridge.sensor.db.entity.SensorDataEntity
import com.braveridge.sensor.db.entity.MoveDetectionEntity
import com.braveridge.sensor.db.entity.TemperatureEntity
import java.time.LocalDateTime

@Mapper
interface MoveDetectionMapper {
    // 全レコード取得
    @Select("""
    <script>
        SELECT * FROM move_detection
        <where>
            <if test="deviceId != null">
                AND device_id = #{deviceId}
            </if>
            <if test="sensorId != null">
                AND sensor_id = #{sensorId}
            </if>
            <if test="afterOrEqualDate != null">
                <![CDATA[
                AND date >= #{afterOrEqualDate}
                ]]>
            </if>
        </where>
    </script>
    """)
    fun findAll(deviceId: String? = null, sensorId: String? = null, afterOrEqualDate: LocalDateTime? = null): List<MoveDetectionEntity>

    // センサーデータ取得
    // 条件：デバイスID、センサID、取得開始日、取得終了日
    // 取得：ルーターID、デバイスID、日時、合計検知数、平均受信強度
    @Select("""
    SELECT device_id, FROM_UNIXTIME(minute * 60) AS date, sum_data, avg_device_rssi
    FROM (
        SELECT
            device_id,
            TRUNCATE(UNIX_TIMESTAMP(`date`) / 60, 0) AS minute,
            SUM(`is_detected`) AS sum_data,
            AVG(`device_rssi`) AS avg_device_rssi
        FROM move_detection
        WHERE device_id = #{deviceId}
            AND sensor_id = #{sensorId}
            AND date >= #{afterOrEqualDate}
        GROUP BY TRUNCATE(UNIX_TIMESTAMP(`date`) / 60, 0)
    ) AS data_per_minute
    ORDER BY date
    """)
    fun findSensorData(deviceId: String, sensorId: String,
                       afterOrEqualDate: LocalDateTime, beforeOrEqualDate: LocalDateTime? = null,
                       perSeconds: Int = 60): List<SensorDataEntity>

    // 平均カウント数を取得
    // 条件：デバイスID、センサID、取得開始日
    @Select("""
        SELECT TRUNCATE(AVG(`count`) + .005, 2)
        FROM move_detection
        WHERE device_id = #{deviceId}
            AND sensor_id = #{sensorId}
            AND date >= #{afterOrEqualDate}
    """)
    fun getAverageValue(deviceId: String, sensorId: String, afterOrEqualDate: LocalDateTime): Double

    //
    @Select("""
    SELECT device_id, FROM_UNIXTIME(minute * 60) AS date, avg_data, avg_device_rssi
    FROM (
        SELECT
            device_id,
            TRUNCATE(UNIX_TIMESTAMP(`date`) / 60, 0) AS minute,
            MAX(`count`) AS avg_data,
            AVG(`device_rssi`) AS avg_device_rssi
        FROM move_detection
        WHERE device_id = #{deviceId}
            AND sensor_id = #{sensorId}
            AND date >= #{afterOrEqualDate}
        GROUP BY TRUNCATE(UNIX_TIMESTAMP(`date`) / 60, 0)
    ) AS data_per_minute
    ORDER BY date
    """)
    fun findSensorCountData(deviceId: String, sensorId: String, afterOrEqualDate: LocalDateTime): List<SensorDataEntity>

    // データ取得
    // 条件：id
    @Select("SELECT * FROM move_detection WHERE id = #{id}")
    fun findById( id: Long ): MoveDetectionEntity?

    // データ取得
    // 条件：デバイスID（最後に追加されたデータを取得する）
    @Select("SELECT * FROM move_detection WHERE device_id = #{deviceId} ORDER BY id DESC LIMIT 1")
    fun lastSensorEntityByDeviceId(deviceId: String): MoveDetectionEntity?

    // データ追加
    @Insert("INSERT INTO move_detection (" +
            "router_id, device_id, device_rssi, sensor_id, is_detected, count, uplink_id, date) VALUES (" +
            "#{routerId}, #{deviceId}, #{deviceRssi}, #{sensorId}, #{isDetected}, #{count}, #{uplinkId}, #{date})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    fun insert(entity: MoveDetectionEntity): Long

    // データ削除
    // 条件：指定日時（指定日時以前のデータを削除する）
    @Delete("DELETE FROM move_detection WHERE date < #{beforeDate} LIMIT #{limit}")
    fun deleteBeforeDate(beforeDate: LocalDateTime, limit: Int = 10000): Long
}