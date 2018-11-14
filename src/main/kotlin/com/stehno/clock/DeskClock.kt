package com.stehno.clock

import java.awt.*
import javax.swing.JFrame
import javax.swing.JPanel

// TODO: https://www.flaticon.com/packs/weather-173

class DeskClock : JFrame("DeskClock") {

    init {
        size = Dimension(600, 800)
        location = Point(800, 800)
        isAlwaysOnTop = true
        defaultCloseOperation = JFrame.EXIT_ON_CLOSE

        contentPane = JPanel(GridLayout(1, 1))
        contentPane.add(ClockCanvas())
    }

    companion object {
        @JvmStatic fun main(args: Array<String>) {
            val clock = DeskClock()
            clock.isVisible = true
        }
    }
}

