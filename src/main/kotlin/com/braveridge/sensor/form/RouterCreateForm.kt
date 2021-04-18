package com.braveridge.sensor.form

import com.fasterxml.jackson.annotation.JsonProperty
import javax.validation.constraints.NotBlank

class RouterCreateForm {
    @NotBlank
    @JsonProperty("router_id")
    lateinit var routerId: String
    @NotBlank
    @JsonProperty("registration_code")
    lateinit var registrationCode: String
    @NotBlank
    lateinit var name: String
}
