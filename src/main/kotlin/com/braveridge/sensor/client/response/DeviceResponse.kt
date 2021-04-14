package com.braveridge.sensor.client.response

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import java.time.ZonedDateTime

@JsonIgnoreProperties(ignoreUnknown = true)
class DeviceResponse {
    @JsonProperty("group_id")
    lateinit var groupId: String
    @JsonProperty("device_id")
    lateinit var deviceId: String
    lateinit var name: String
    lateinit var sensors: List<Sensor>
    @JsonProperty("created_at")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssxxxxx")
    lateinit var createdAt: ZonedDateTime
    @JsonProperty("updated_at")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssxxxxx")
    lateinit var updatedAt: ZonedDateTime

    @JsonIgnoreProperties(ignoreUnknown=true)
    class Sensor {
        @JsonProperty("sensor_id")
        lateinit var sensorId: String
        lateinit var name: String
        @JsonProperty("executable_commands")
        lateinit var executableCommands: List<String>
    }
}
