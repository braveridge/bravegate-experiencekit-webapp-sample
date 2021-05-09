package com.braveridge.experiencekit.client.response

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown=true)
class RouterListResponse {
    lateinit var routers: List<RouterResponse>
    var total: Int = 0
    var pages: Int = 0
    var limit: Int = 0
    @JsonProperty("current_page")
    var currentPage: Int = 0
    @JsonProperty("next_page")
    var nextPage: String? = null
}
