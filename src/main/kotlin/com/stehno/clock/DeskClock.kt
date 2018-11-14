package com.stehno.clock

import java.awt.Dimension
import java.awt.GridLayout
import java.awt.Point
import java.lang.reflect.Executable
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
        size = Dimension(450, 260)
        location = Point(800, 800)
        isAlwaysOnTop = true
        isResizable = false
        defaultCloseOperation = JFrame.EXIT_ON_CLOSE

        contentPane = JPanel(GridLayout(1, 1))
        contentPane.add(ClockCanvas(zipCode, apiKey))
    }
}

object Scheduler {
    val INSTANCE = Executors.newSingleThreadScheduledExecutor()
}

