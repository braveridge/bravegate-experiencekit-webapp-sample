package com.braveridge.sensor.utility

import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime

fun ZonedDateTime.toLocal(): LocalDateTime = withZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime()