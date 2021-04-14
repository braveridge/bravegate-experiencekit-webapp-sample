package com.braveridge.sensor.client.response

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import java.time.ZonedDateTime

@JsonIgnoreProperties(ignoreUnknown=true)
class RouterResponse {
    @JsonProperty("group_id")
    lateinit var groupId: String
    @JsonProperty("router_id")
    lateinit var routerId: String
    lateinit var imsi: String
    var rssi: Int = 0
    var battery: Int = 0
    @JsonProperty("sim_status")
    lateinit var simStatus: String
    lateinit var name: String
    @JsonProperty("created_at")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssxxxxx")
    lateinit var createdAt: ZonedDateTime
    @JsonProperty("updated_at")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssxxxxx")
    lateinit var updatedAt: ZonedDateTime
}
