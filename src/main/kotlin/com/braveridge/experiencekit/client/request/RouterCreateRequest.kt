package com.braveridge.experiencekit.client.request

import com.fasterxml.jackson.annotation.JsonProperty

data class RouterCreateRequest(
    @field:JsonProperty("group_id")
    val groupId: String,
    @field:JsonProperty("router_id")
    val routerId: String,
    @field:JsonProperty("registration_code")
    val registrationCode: String,
    val name: String
)
