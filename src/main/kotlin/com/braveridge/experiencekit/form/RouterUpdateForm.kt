package com.braveridge.experiencekit.form

import com.fasterxml.jackson.annotation.JsonProperty
import javax.validation.constraints.NotBlank

class RouterUpdateForm {
    @NotBlank
    @JsonProperty("group_id")
    lateinit var groupId: String

    @NotBlank
    lateinit var name: String
}