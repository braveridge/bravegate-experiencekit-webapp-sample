package com.braveridge.sensor.client

import java.io.Serializable

data class AuthToken(val apiKey: String, val token: String) : Serializable
