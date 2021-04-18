package com.braveridge.sensor.client.response

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import java.time.ZonedDateTime

@JsonIgnoreProperties(ignoreUnknown=true)
class ApplicationResponse {
    @JsonProperty("group_id")
    lateinit var groupId: String
    @JsonProperty("application_id")
    lateinit var applicationId: String
    lateinit var name: String
    @JsonProperty("application_type")
    lateinit var applicationType: String
    lateinit var settings: Setting
    @JsonProperty("created_at")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssxxxxx")
    lateinit var createdAt: ZonedDateTime
    @JsonProperty("updated_at")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssxxxxx")
    lateinit var updatedAt: ZonedDateTime

    @JsonIgnoreProperties(ignoreUnknown=true)
    class Setting {
        lateinit var url: String
        var token: String? = null
    }
}