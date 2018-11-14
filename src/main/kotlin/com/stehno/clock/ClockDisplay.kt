package com.stehno.clock

import java.awt.Canvas
import java.awt.Color
import java.awt.Font
import java.awt.Graphics
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.concurrent.TimeUnit

class ClockDisplay(private val canvas: Canvas) {

    companion object {
        private val FONT = Font.createFont(Font.TRUETYPE_FONT, ClockDisplay::class.java.getResourceAsStream("/DIGITALDREAM.ttf"))
        private val FORMATTER = DateTimeFormatter.ofPattern("hh:mm a")
    }

    private var currentTime = currentTime()

    init {
        // repaint only the clock area
        Scheduler.INSTANCE.scheduleAtFixedRate({
            val time = currentTime()
            if (time != currentTime) {
                currentTime = time
                canvas.repaint(0, 5, canvas.size.width, 80)
            }
        }, 10, 10, TimeUnit.SECONDS)
    }

    fun draw(g: Graphics) {
        g.color = Color.GREEN
        g.font = FONT.deriveFont(64f)
        g.drawString(currentTime, 10, 65)
    }

    private fun currentTime(): String {
        return LocalTime.now().format(FORMATTER)
    }
}