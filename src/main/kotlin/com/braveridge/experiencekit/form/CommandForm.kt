package com.braveridge.partnerconference.form.ifc

import javax.validation.Valid
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

class CommandForm {
    @NotBlank
    lateinit var name: String

    @NotNull
    lateinit var params: Map<String, Any>

    @NotNull
    @Valid
    lateinit var targets: Target

    class Target {
        @NotNull
        lateinit var devices: List<String>

        @NotNull
        lateinit var routers: List<String>

        @NotNull
        lateinit var groups: List<String>
    }
}
