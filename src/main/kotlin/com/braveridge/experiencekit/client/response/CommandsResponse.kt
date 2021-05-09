package com.braveridge.experiencekit.client.response

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import java.time.ZonedDateTime

@JsonIgnoreProperties(ignoreUnknown=true)
class CommandsResponse {
    lateinit var commands: List<Command>
    var total: Int = 0
    var pages: Int = 0
    var limit: Int = 0
    @JsonProperty("current_page")
    var currentPage: Int = 0
    @JsonProperty("next_page")
    var nextPage: String? = null

    @JsonIgnoreProperties(ignoreUnknown=true)
    class Command {
        @JsonProperty("command_id")
        lateinit var commandId: String
        lateinit var name: String
        lateinit var url: String
        @JsonProperty("status_counts")
        lateinit var statusCounts: StatusCount
        var status: String? = null
        @JsonProperty("accepted_at")
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssxxxxx")
        lateinit var acceptedAt: ZonedDateTime
    }

    @JsonIgnoreProperties(ignoreUnknown=true)
    class StatusCount {
        var processed: Int = 0
        var rejected: Int = 0
        var pending: Int = 0
    }
}