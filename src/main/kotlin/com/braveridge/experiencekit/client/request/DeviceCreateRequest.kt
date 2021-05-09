package com.braveridge.experiencekit.client.request

import com.fasterxml.jackson.annotation.JsonProperty

data class DeviceCreateRequest(
    @field:JsonProperty("group_id")
    val groupId: String,
    @field:JsonProperty("device_id")
    val deviceId: String,
    @field:JsonProperty("registration_code")
    val registrationCode: String,
    val name: String
)
