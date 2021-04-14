package com.braveridge.sensor.client.response

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown=true)
class AccountListResponse {
    lateinit var accounts: List<AccountResponse>
    var total: Int = 0
    var pages: Int = 0
    var limit: Int = 0
    @JsonProperty("current_page")
    var currentPage: Int = 0
    @JsonProperty("next_page")
    var nextPage: String? = null
}
