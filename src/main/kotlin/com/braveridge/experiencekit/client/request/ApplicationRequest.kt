package com.braveridge.experiencekit.client.request

import com.fasterxml.jackson.annotation.JsonProperty

data class ApplicationRequest(
    @field:JsonProperty("group_id")
    val groupId: String,
    val settings: Settings,
    @field:JsonProperty("application_type")
    val applicationType: String,
    val name: String
) {
    data class Settings(
        val url: String,
        val token: String?
    )
}