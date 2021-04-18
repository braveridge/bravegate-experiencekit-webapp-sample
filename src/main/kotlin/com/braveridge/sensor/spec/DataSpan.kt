package com.braveridge.sensor.spec

import java.io.Serializable

enum class DataSpan(val rawValue: Int) : Serializable {
    Hour(1),
    Day(2),
    Week(3),
    Days3(4),
    Month(5),
    Unknown(0);

    companion object {
        fun of(value: Int): DataSpan {
            return values().firstOrNull { it.rawValue == value } ?: Unknown
        }
    }
}
