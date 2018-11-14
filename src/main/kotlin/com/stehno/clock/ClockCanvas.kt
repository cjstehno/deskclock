package com.stehno.clock

import java.awt.Canvas
import java.awt.Color
import java.awt.Graphics

class ClockCanvas(zipCode: String, apiKey: String) : Canvas() {

    private val clockDisplay = ClockDisplay(this)
    private val dateDisplay = DateDisplay(this)
    private val weatherDisplay = WeatherDisplay(this, zipCode, apiKey)

    override fun paint(g: Graphics) {
        draw(g)
        clockDisplay.draw(g)
        dateDisplay.draw(g)
        weatherDisplay.draw(g)
    }

    private fun draw(g: Graphics) {
        val size = parent.size
        g.color = Color.DARK_GRAY
        g.fillRect(0, 0, size.width, size.height)
    }
}