package com.stehno.clock

import com.beust.klaxon.JsonObject
import com.beust.klaxon.Parser
import okhttp3.OkHttpClient
import okhttp3.Request
import java.awt.*
import java.util.concurrent.TimeUnit
import javax.swing.ImageIcon

class WeatherDisplay(private val zipCode: String, private val apiKey: String) : Canvas() {

    companion object {
        private const val BASE_URL = "http://api.openweathermap.org/data/2.5/weather"
        private val FONT = Font("Sans Serif", Font.PLAIN, 24)

        private fun resolveWeatherImage(code: String?): String {
            return when (code) {
                in arrayOf("01d", "01n") -> "sun.png"
                in arrayOf("02d", "02n") -> "cloud-sun.png"
                in arrayOf("03d", "03n", "04d", "04n") -> "cloud.png"
                in arrayOf("09d", "09n", "10d", "10n") -> "rainy.png"
                in arrayOf("11d", "11n") -> "storm.png"
                in arrayOf("13d", "13n") -> "snowing.png"
                in arrayOf("50d", "50n") -> "fog.png"
                else -> "temperature.png"
            }
        }

        private fun loadImage(path: String) = ImageIcon(WeatherDisplay::class.java.getResource(path))
    }

    private var icon = loadImage("/temperature.png")
    private var display = "-"

    init {
        preferredSize = Dimension(225, 75)

        updateWeather()

        Scheduler.INSTANCE.scheduleAtFixedRate({
            if (updateWeather()) {
                repaint()
            }
        }, 30, 30, TimeUnit.MINUTES)
    }

    override fun paint(g: Graphics) {
        g.drawImage(icon.image, 0, 5, 64, 64, null)

        g.color = Color.LIGHT_GRAY
        g.font = FONT
        g.drawString(display, 75, 45)
    }

    private fun updateWeather(): Boolean {
        val info = currentWeather()

        val conditions = resolveWeatherImage(info?.conditions)
        val newIcon = loadImage("/$conditions")
        val newDisplay = info.toString()

        val updated = newIcon != icon || newDisplay != display

        icon = newIcon
        display = newDisplay

        return updated
    }

    private fun currentWeather(): WeatherInfo? {
        val jsonStr = OkHttpClient.Builder().build().newCall(
            Request.Builder().url("$BASE_URL?zip=$zipCode&appid=$apiKey&units=imperial").get().build()
        ).execute().body()?.string()

        return when {
            jsonStr != null -> {
                val json: JsonObject = Parser().parse(StringBuilder(jsonStr)) as JsonObject
                WeatherInfo(
                    json.array<JsonObject>("weather")!![0].string("icon")!!,
                    json.obj("main")!!.double("temp")!!,
                    json.obj("wind")!!.double("speed")!!
                )
            }
            else -> null
        }
    }
}