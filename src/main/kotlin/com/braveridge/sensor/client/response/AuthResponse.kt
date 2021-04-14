package com.braveridge.sensor.client.response

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import java.time.ZonedDateTime

@JsonIgnoreProperties(ignoreUnknown=true)
class AuthResponse {
    @JsonProperty("apikey")
    lateinit var apiKey: String
    lateinit var token: String
    @JsonProperty("expire_at")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssxxxxx")
    lateinit var expireAt: ZonedDateTime
}
