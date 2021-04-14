package com.braveridge.sensor.db.mapper

import org.apache.ibatis.annotations.*
import com.braveridge.sensor.db.entity.SensorDataEntity
import com.braveridge.sensor.db.entity.SensorLogEntity
import com.braveridge.sensor.db.entity.TemperatureEntity
import java.time.LocalDateTime

@Mapper
interface TemperatureMapper {
    // 全レコード取得
    @Select("""
    <script>
        SELECT * FROM temperature
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
        ORDER BY date
    </script>
    """)
    fun findAll(deviceId: String? = null, sensorId: String? = null, afterOrEqualDate: LocalDateTime? = null): List<TemperatureEntity>

    // センサーデータ取得
    // 条件：デバイスID、センサID、取得開始日、取得終了日
    @Select("""
    <script>
        SELECT device_id, FROM_UNIXTIME(minute * #{perSeconds}) AS date, avg_data, avg_device_rssi
        FROM (
            SELECT
                device_id,
                TRUNCATE(UNIX_TIMESTAMP(`date`) / #{perSeconds}, 0) AS minute,
                AVG(`value`) AS avg_data,
                AVG(`device_rssi`) AS avg_device_rssi
            FROM temperature
            WHERE device_id = #{deviceId}
                AND sensor_id = #{sensorId}
                <![CDATA[
                AND date >= #{afterOrEqualDate}
                ]]>
                <if test="beforeOrEqualDate != null">
                    <![CDATA[
                    AND date <= #{beforeOrEqualDate}
                    ]]>
                </if>
            GROUP BY TRUNCATE(UNIX_TIMESTAMP(`date`) / #{perSeconds}, 0)
        ) AS data_per_minute
        ORDER BY date
    </script>
    """)
    fun findSensorData(deviceId: String, sensorId: String,
                       afterOrEqualDate: LocalDateTime, beforeOrEqualDate: LocalDateTime? = null,
                       perSeconds: Int = 60): List<SensorDataEntity>

    // センサーデータ取得
    // 条件：ルーターID、センサID、取得開始日、取得終了日
    // 取得：ルーターID、デバイスID、日時、平均温度、平均受信強度
    @Select("""
    <script>
        SELECT router_id AS device_id, FROM_UNIXTIME(minute * #{perSeconds}) AS date, avg_data, avg_device_rssi
        FROM (
            SELECT
                router_id,
                TRUNCATE(UNIX_TIMESTAMP(`date`) / #{perSeconds}, 0) AS minute,
                AVG(`temperature`) AS avg_data,
                AVG(`device_rssi`) AS avg_device_rssi
            FROM temperature
            WHERE router_id = #{routerId}
                AND sensor_id = #{sensorId}
                <![CDATA[
                AND date >= #{afterOrEqualDate}
                ]]>
                <if test="beforeOrEqualDate != null">
                    <![CDATA[
                    AND date <= #{beforeOrEqualDate}
                    ]]>
                </if>
                <if test="minRssi != null">
                    <![CDATA[
                    AND device_rssi >= #{minRssi}
                    ]]>
                </if>
            GROUP BY TRUNCATE(UNIX_TIMESTAMP(`date`) / #{perSeconds}, 0)
        ) AS data_per_minute
        ORDER BY date
    </script>
    """)
    fun findSensorDataByRouter(routerId: String, sensorId: String,
                               afterOrEqualDate: LocalDateTime, beforeOrEqualDate: LocalDateTime? = null,
                               minRssi: Int? = null, perSeconds: Int = 60): List<SensorDataEntity>

    // 平均温度を取得
    // 条件：デバイスID、センサID、取得開始日
    @Select("""
        SELECT TRUNCATE(AVG(`temperature`) + .005, 2)
        FROM temperature
        WHERE device_id = #{deviceId}
            AND sensor_id = #{sensorId}
            AND date >= #{afterOrEqualDate}
    """)
    fun getAverageValue(deviceId: String, sensorId: String, afterOrEqualDate: LocalDateTime): Double

    // データ取得
    // 条件：id
    @Select("SECLCT * FROM temperature WHERE id = #{id}")
    fun findById( id: Long ): TemperatureEntity?

    // データ取得
    // 条件：デバイスID（最後に追加されたデータを取得する）
    @Select("SELECT * FROM temperature WHERE device_id = #{deviceId} ORDER BY date DESC LIMIT 1")
    fun lastSensorEntityByDeviceId(deviceId: String): TemperatureEntity?

    // データ追加
    @Insert("INSERT INTO temperature (" +
            "router_id, device_id, device_rssi, sensor_id, temperature, uplink_id, date) VALUES (" +
            "#{routerId}, #{deviceId}, #{deviceRssi}, #{sensorId}, #{temperature}, #{uplinkId}, #{date})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    fun insert(entity: TemperatureEntity): Long

    // データ削除
    // 条件：指定日時（指定日時以前のデータを削除する）
    @Delete("DELETE FROM temperature WHERE date < #{beforeDate} LIMIT #{limit}")
    fun deleteBeforeDate(beforeDate: LocalDateTime, limit: Int = 10000): Long
}