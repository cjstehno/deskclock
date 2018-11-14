package com.stehno.clock

import java.awt.Canvas
import java.awt.Color
import java.awt.Font
import java.awt.Graphics
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.concurrent.TimeUnit

class DateDisplay(private val canvas: Canvas) {

    companion object {
        private val FORMATTER = DateTimeFormatter.ofPattern("EEEE, MMMM d")
    }

    private var currentDate: String = calculateDate()

    init {
        // Fires at 1s after midnight (just to be sure)
        Scheduler.INSTANCE.schedule(
            {
                val date = calculateDate()
                if (date != currentDate) {
                    currentDate = date
                    canvas.repaint(0, 85, canvas.size.width, 38)
                }
            },
            (LocalTime.of(23, 59, 59).toSecondOfDay() - LocalTime.now().toSecondOfDay()).toLong() + 2,
            TimeUnit.SECONDS
        )
    }

    fun draw(g: Graphics) {
        g.color = Color.LIGHT_GRAY
        g.font = Font("Sans Serif", Font.PLAIN, 28)
        g.drawString(currentDate, 20, 115)
    }

    private fun calculateDate() = LocalDate.now().format(FORMATTER)
}