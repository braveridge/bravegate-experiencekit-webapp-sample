package com.braveridge.sensor.form

import com.fasterxml.jackson.annotation.JsonProperty
import javax.validation.constraints.NotBlank

class DeviceCreateForm {
    @NotBlank
    @JsonProperty("device_id")
    lateinit var deviceId: String
    @NotBlank
    @JsonProperty("registration_code")
    lateinit var registrationCode: String
    @NotBlank
    lateinit var name: String
}
