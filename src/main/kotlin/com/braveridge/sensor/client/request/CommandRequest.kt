package com.braveridge.sensor.client.request

import com.braveridge.partnerconference.form.ifc.CommandForm


data class CommandRequest(
    val name: String,
    val params: Map<String, Any>,
    val targets: CommandForm.Target
) {

    data class Target(val devices: List<String>,
                      val routers: List<String>,
                      val groups: List<String>)

}
