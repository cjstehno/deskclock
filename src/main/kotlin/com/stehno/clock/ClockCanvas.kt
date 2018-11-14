package com.stehno.clock

import java.awt.Canvas
import java.awt.Color
import java.awt.Font
import java.awt.Graphics
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import javax.swing.ImageIcon

class ClockCanvas : Canvas() {

    private val clock = ClockDisplay(this)

    override fun paint(g: Graphics) {
        g.color = Color.BLACK
        g.fillRect(0, 0, 600, 800) // FIXME: derive this

        clock.draw(g)

        g.color = Color.DARK_GRAY
        g.font = Font("Serif", Font.PLAIN, 32)
        g.drawString("Thursday, November 13", 25, 250)

        g.drawImage(ImageIcon(ClockCanvas::class.java.getResource("/cloud.png")).image, 25, 300, 64, 64, null)

        g.drawString("55 F (10 mph E)", 85, 340)
    }
}

class ClockDisplay(private val canvas: Canvas) {

    // FIXME: need means of updating the time

    companion object {
        private val FONT = Font.createFont(Font.TRUETYPE_FONT, ClockDisplay::class.java.getResourceAsStream("/DIGITALDREAM.ttf"))
        private val TIMER = Executors.newSingleThreadScheduledExecutor()
        private val FORMATTER = DateTimeFormatter.ofPattern("HH:mm")
    }

    init {
        TIMER.scheduleAtFixedRate(canvas::repaint, 10, 10, TimeUnit.SECONDS)
    }

    fun draw(g: Graphics) {
        g.color = Color.GREEN
        g.font = FONT.deriveFont(64f)
        g.drawString(LocalTime.now().format(FORMATTER), 10, 65)
        // FIXME: need am/pm flag
    }
}