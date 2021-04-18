package com.braveridge.sensor.db.mapper

import org.apache.ibatis.annotations.*
import com.braveridge.sensor.db.entity.SensorLogEntity
import java.time.LocalDateTime

@Mapper
interface SensorLogMapper {
    // 全レコード取得
    @Select("""
    <script>
        SELECT * FROM sensor_log
        <where>
            <if test="appId != null">
                AND application_id = #{appId}
            </if>
            <if test="routerId != null">
                AND router_id = #{routerId}
            </if>
            <if test="deviceId != null">
                AND device_id = #{deviceId}
            </if>
            <if test="sensorId != null">
                AND sensor_id = #{sensorId}
            </if>
        </where>
        ORDER BY id DESC, id DESC LIMIT #{limit} OFFSET #{offset}
    </script>
    """)
    fun findAll(limit: Int, offset: Int, appId: String? = null, routerId: String? = null, deviceId: String? = null, sensorId: String? = null): List<SensorLogEntity>

    // 全レコード数取得
    @Select("""
    <script>
        SELECT COUNT(*) FROM sensor_log
        <where>
            <if test="appId != null">
                AND application_id = #{appId}
            </if>
            <if test="routerId != null">
                AND router_id = #{routerId}
            </if>
            <if test="deviceId != null">
                AND device_id = #{deviceId}
            </if>
            <if test="sensorId != null">
                AND sensor_id = #{sensorId}
            </if>
        </where>
    </script>
    """)
    fun totalCount(appId: String? = null, routerId: String? = null, deviceId: String? = null, sensorId: String? = null): Long

    // データ取得
    // 条件：id
    @Select("SELECT * FROM sensor_log WHERE id = #{id}")
    fun findById( id: Long ): SensorLogEntity?

    // データ追加
    @Insert("INSERT INTO sensor_log (" +
            "application_id, application_name, router_id, router_imsi, router_rssi, router_battery, router_fw_version, " +
            "device_id, sensor_id, sensor_name, device_rssi, data, uplink_id, date, json_string) VALUES (" +
            "#{applicationId}, #{applicationName}, #{routerId}, #{routerImsi}, #{routerRssi}, #{routerBattery}, #{routerFwVersion}, " +
            "#{deviceId}, #{sensorId}, #{sensorName}, #{deviceRssi}, #{data}, #{uplinkId}, #{date}, #{jsonString})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    fun insert(entity: SensorLogEntity): Long

    // データ削除
    // 条件：指定日時（指定日時以前のデータを削除する）
    @Delete("DELETE FROM sensor_log WHERE date < #{beforeDate} LIMIT #{limit}")
    fun deleteBeforeDate(beforeDate: LocalDateTime, limit: Int = 10000): Long
}