package com.stehno.clock

import java.awt.Color
import java.awt.Dimension
import java.awt.FlowLayout
import java.awt.Point
import java.util.concurrent.Executors
import javax.swing.JFrame
import javax.swing.JPanel

class DeskClock(zipCode: String, apiKey: String) : JFrame("DeskClock") {

    companion object {
        // zip key
        @JvmStatic fun main(args: Array<String>) {
            val clock = DeskClock(args[0], args[1])
            clock.isVisible = true
        }
    }

    init {
        size = Dimension(250, 210)
        location = Point(800, 800)
        isAlwaysOnTop = true
//        isResizable = false
        defaultCloseOperation = JFrame.EXIT_ON_CLOSE

        val panel = JPanel(FlowLayout(FlowLayout.CENTER, 0, 0))
        panel.background = Color.DARK_GRAY

        contentPane = panel
        contentPane.add(ClockDisplay())
        contentPane.add(DateDisplay())
        contentPane.add(WeatherDisplay(zipCode, apiKey))
    }
}

object Scheduler {
    val INSTANCE = Executors.newSingleThreadScheduledExecutor()
}

