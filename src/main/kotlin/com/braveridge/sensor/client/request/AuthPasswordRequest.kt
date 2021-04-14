package com.braveridge.sensor.client.request

import com.fasterxml.jackson.annotation.JsonProperty

data class AuthPasswordRequest(
        @field:JsonProperty("email_address")
        val email: String,
        val password: String,
        @field:JsonProperty("timeout_seconds")
        val timeoutSeconds: Long = 86400)