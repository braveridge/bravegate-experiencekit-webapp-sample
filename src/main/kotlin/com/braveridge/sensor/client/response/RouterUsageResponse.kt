package com.braveridge.sensor.client.response

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDate

@JsonIgnoreProperties(ignoreUnknown=true)
class RouterUsageResponse {
    @JsonProperty("data_usage")
    lateinit var dataUsage: List<DataUsage>
    @JsonProperty("total_uplink_byte")
    var totalUplinkByte: Int = 0
    @JsonProperty("total_downlink_byte")
    var totalDownlinkByte: Int = 0

    @JsonIgnoreProperties(ignoreUnknown=true)
    class DataUsage {
        @JsonFormat(pattern = "yyyyMMdd")
        lateinit var date: LocalDate
        @JsonProperty("uplink_byte")
        var uplinkByte: Int = 0
        @JsonProperty("downlink_byte")
        var downlinkByte: Int = 0
    }
}
