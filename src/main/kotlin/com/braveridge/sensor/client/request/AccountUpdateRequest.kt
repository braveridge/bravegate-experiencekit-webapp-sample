package com.braveridge.sensor.client.request

import com.fasterxml.jackson.annotation.JsonProperty

data class AccountUpdateRequest(
        @field:JsonProperty("first_name")
        val firstName: String,
        @field:JsonProperty("last_name")
        val lastName: String,
        @field:JsonProperty("phone_number")
        val tel: String,
        @field:JsonProperty("company_name")
        val company: String,
        val timezone: String = "Asia/Tokyo"
)