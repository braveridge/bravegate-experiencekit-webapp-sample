package com.braveridge.sensor.client.request

data class CommandRequest(
        val name: String,
        val params: Map<String, Any>,
        val targets: Target) {

    data class Target(val devices: List<String>,
                      val routers: List<String>,
                      val groups: List<String>)

}
