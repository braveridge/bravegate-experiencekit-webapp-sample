package com.braveridge.sensor.client.response

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import java.time.ZonedDateTime

@JsonIgnoreProperties(ignoreUnknown=true)
class ApiKeyResponse {
    lateinit var apikey: String
    @JsonProperty("authkey_id")
    lateinit var authkeyId: String
    @JsonProperty("authkey_secret")
    lateinit var authkeySecret: String
    lateinit var name: String
    @JsonProperty("created_at")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssxxxxx")
    lateinit var createdAt: ZonedDateTime
    @JsonProperty("updated_at")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssxxxxx")
    lateinit var updatedAt: ZonedDateTime
}