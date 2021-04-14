package com.braveridge.sensor.client.request

import com.fasterxml.jackson.annotation.JsonProperty

data class ApplicationRequest(
        @field:JsonProperty("group_id")
        val groupId: String,
        val settings: Setting,
        @field:JsonProperty("application_type")
        val applicationType: String,
        val name: String) {
    data class Setting(val url: String)
}