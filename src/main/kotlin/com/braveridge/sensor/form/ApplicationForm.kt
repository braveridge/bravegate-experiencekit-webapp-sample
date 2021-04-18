package com.braveridge.sensor.form

import com.fasterxml.jackson.annotation.JsonProperty
import javax.validation.constraints.NotBlank

class ApplicationForm {
    @JsonProperty("application_id")
    lateinit var applicationId: String
    @NotBlank
    lateinit var url: String
    @NotBlank
    lateinit var name: String
    var token: String? = null
}