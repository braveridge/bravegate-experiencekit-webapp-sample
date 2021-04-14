package com.braveridge.sensor.client.request

import com.fasterxml.jackson.annotation.JsonProperty

data class GroupRequest(
        val name: String,
        @field:JsonProperty("is_default")
        val default: Boolean)