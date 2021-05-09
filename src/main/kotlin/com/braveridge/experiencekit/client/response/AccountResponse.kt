package com.braveridge.experiencekit.client.response

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import java.time.ZoneId
import java.time.ZonedDateTime

@JsonIgnoreProperties(ignoreUnknown=true)
class AccountResponse {
    @JsonProperty("account_id")
    lateinit var accountId: String
    @JsonProperty("email_address")
    lateinit var emailAddress: String
    @JsonProperty("phone_number")
    lateinit var phoneNumber: String
    @JsonProperty("first_name")
    lateinit var firstName: String
    @JsonProperty("last_name")
    lateinit var lastName: String
    @JsonProperty("company_name")
    lateinit var companyName: String
    lateinit var timezone: ZoneId
    @JsonProperty("created_at")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssxxxxx")
    lateinit var createdAt: ZonedDateTime
    @JsonProperty("updated_at")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssxxxxx")
    lateinit var updatedAt: ZonedDateTime
}
