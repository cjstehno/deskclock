package com.stehno.clock

import java.text.DecimalFormat

data class WeatherInfo(
    val conditions: String,
    val temperature: Double,
    val winds: Double
){

    companion object {
        private val FORMATTER = DecimalFormat("0")
    }

    override fun toString() = "${FORMATTER.format(temperature)} ℉ (${FORMATTER.format(winds)} m/h)"
}