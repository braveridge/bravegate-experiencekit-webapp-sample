package com.braveridge.sensor.client.response

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import java.time.ZonedDateTime

@JsonIgnoreProperties(ignoreUnknown=true)
class CommandResponse {
    @JsonProperty("command_id")
    lateinit var commandId: String
    lateinit var name: String
    lateinit var params: Map<String, Any>
    @JsonProperty("command_seq")
    var commandSequence: Int = 0
    var status: String? = null
    lateinit var targets: Target
    @JsonProperty("accepted_at")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssxxxxx")
    lateinit var acceptedAt: ZonedDateTime

    @JsonIgnoreProperties(ignoreUnknown=true)
    class Target {
        var groups: List<Group>? = null
        var routers: List<Router>? = null
        var devices: List<Device>? = null
    }

    @JsonIgnoreProperties(ignoreUnknown=true)
    class Group {
        @JsonProperty("group_id")
        lateinit var groupId: String
        var reason: String? = null
        lateinit var status: String
        @JsonProperty("sent_at")
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssxxxxx")
        var sentAt: ZonedDateTime? = null
        @JsonProperty("received_at")
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssxxxxx")
        var receivedAt: ZonedDateTime? = null
    }

    @JsonIgnoreProperties(ignoreUnknown=true)
    class Router {
        @JsonProperty("router_id")
        var routerId: String? = null
        var reason: String? = null
        lateinit var status: String
        @JsonProperty("sent_at")
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssxxxxx")
        var sentAt: ZonedDateTime? = null
        @JsonProperty("received_at")
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssxxxxx")
        var receivedAt: ZonedDateTime? = null
    }

    @JsonIgnoreProperties(ignoreUnknown=true)
    class Device {
        @JsonProperty("device_id")
        lateinit var deviceId: String
        @JsonProperty("router_id")
        var routerId: String? = null
        var reason: String? = null
        lateinit var status: String
        @JsonProperty("sent_at")
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssxxxxx")
        var sentAt: ZonedDateTime? = null
        @JsonProperty("received_at")
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssxxxxx")
        var receivedAt: ZonedDateTime? = null
    }
}
