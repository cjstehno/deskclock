package com.stehno.clock

import java.awt.*
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.concurrent.TimeUnit

class DateDisplay : Canvas() {

    companion object {
        private val FORMATTER = DateTimeFormatter.ofPattern("EEE, MMM d")
        private val FONT = Font("Sans Serif", Font.PLAIN, 24)

        private fun calculateDelay(): Long {
            return (LocalTime.of(23, 59, 59).toSecondOfDay() - LocalTime.now().toSecondOfDay()).toLong() + 2
        }
    }

    private var currentDate: String = calculateDate()

    init {
        preferredSize = Dimension(225, 45)

        // Fires at 1s after midnight (just to be sure)
        Scheduler.INSTANCE.schedule(
            this::updateDate,
            calculateDelay(),
            TimeUnit.SECONDS
        )
    }

    override fun paint(g: Graphics) {
        g.color = Color.LIGHT_GRAY
        g.font = FONT
        g.drawString(currentDate, 45, 30)
    }

    private fun updateDate() {
        currentDate = calculateDate()
        repaint()

        Scheduler.INSTANCE.schedule(this::updateDate, calculateDelay(), TimeUnit.SECONDS)
    }

    private fun calculateDate() = LocalDate.now().format(FORMATTER)
}