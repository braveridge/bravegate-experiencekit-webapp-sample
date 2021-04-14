package com.braveridge.sensor.client.request

import com.fasterxml.jackson.annotation.JsonProperty

data class AuthRequest(
        @field:JsonProperty("authkey_id")
        val authkeyId: String,
        @field:JsonProperty("authkey_secret")
        val authkeySecret: String,
        @field:JsonProperty("timeout_seconds")
        val timeoutSeconds: Long = 86400)