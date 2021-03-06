package com.braveridge.experiencekit.client.request

import com.fasterxml.jackson.annotation.JsonProperty

data class DeviceUpdateRequest(
    @field:JsonProperty("group_id")
    val groupId: String,
    val name: String
)
