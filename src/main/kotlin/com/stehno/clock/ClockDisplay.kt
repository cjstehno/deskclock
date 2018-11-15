package com.stehno.clock

import java.awt.Canvas
import java.awt.Color
import java.awt.Dimension
import java.awt.Font.TRUETYPE_FONT
import java.awt.Font.createFont
import java.awt.Graphics
import java.time.LocalTime
import java.time.format.DateTimeFormatter.ofPattern
import java.util.concurrent.TimeUnit

class ClockDisplay : Canvas() {

    companion object {
        private val FONT = createFont(TRUETYPE_FONT, ClockDisplay::class.java.getResourceAsStream("/DIGITALDREAM.ttf"))
        private val FORMATTER = ofPattern("hh:mm a")
    }

    private var currentTime = currentTime()

    init {
        preferredSize = Dimension(225, 50)

        Scheduler.INSTANCE.scheduleAtFixedRate({
            val time = currentTime()
            if (time != currentTime) {
                currentTime = time
                repaint()
            }
        }, 10, 10, TimeUnit.SECONDS)
    }

    override fun paint(g: Graphics) {
        g.color = Color.GREEN
        g.font = FONT.deriveFont(32f)
        g.drawString(currentTime, 10, 35)
    }

    private fun currentTime(): String {
        return LocalTime.now().format(FORMATTER)
    }
}