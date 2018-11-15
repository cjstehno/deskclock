package com.stehno.clock

import java.awt.Canvas
import java.awt.Color
import java.awt.Dimension
import java.awt.Font.TRUETYPE_FONT
import java.awt.Font.createFont
import java.awt.Graphics
import java.time.LocalTime
import java.time.format.DateTimeFormatter.ofPattern
import java.time.temporal.ChronoUnit
import java.util.concurrent.TimeUnit

class ClockDisplay : Canvas() {

    companion object {
        private val FONT = createFont(TRUETYPE_FONT, ClockDisplay::class.java.getResourceAsStream("/DIGITALDREAM.ttf"))
        private val FORMATTER = ofPattern("hh:mm a")

        private fun calculateDelay(): Long {
            val now = LocalTime.now()
            val minute = now.minute + 1

            var minuteMark = minute % 1
            minuteMark = when (minuteMark == 0) {
                true -> minute
                else -> minute - minuteMark + 1
            }

            var hour = now.hour
            if (minuteMark == 60) {
                hour++
                minuteMark = 0
            }

            return now.until(LocalTime.of(hour, minuteMark, 0, 0), ChronoUnit.MILLIS) + 1000 // add 1s for wiggle
        }
    }

    private var currentTime = currentTime()

    init {
        preferredSize = Dimension(225, 50)

        Scheduler.INSTANCE.schedule(this::updateTime, calculateDelay(), TimeUnit.MILLISECONDS)
    }

    override fun paint(g: Graphics) {
        g.color = Color.GREEN
        g.font = FONT.deriveFont(32f)
        g.drawString(currentTime, 10, 35)
    }

    private fun updateTime() {
        currentTime = currentTime()
        repaint()

        Scheduler.INSTANCE.schedule(this::updateTime, calculateDelay(), TimeUnit.MILLISECONDS)
    }

    private fun currentTime() = LocalTime.now().format(FORMATTER)
}