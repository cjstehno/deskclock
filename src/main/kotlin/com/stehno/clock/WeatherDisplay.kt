package com.stehno.clock

import com.beust.klaxon.JsonObject
import com.beust.klaxon.Parser
import okhttp3.OkHttpClient
import okhttp3.Request
import java.awt.Canvas
import java.awt.Color
import java.awt.Graphics
import java.util.concurrent.TimeUnit
import javax.swing.ImageIcon

class WeatherDisplay(private val canvas: Canvas,
                     private val zipCode: String,
                     private val apiKey: String) {

    companion object {
        private const val BASE_URL = "http://api.openweathermap.org/data/2.5/weather"

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
    }

    private var icon = ImageIcon(ClockCanvas::class.java.getResource("/temperature.png"))
    private var temp: Double = 0.0
    private var wind: Double = 0.0

    init {
        updateWeather()

        Scheduler.INSTANCE.scheduleAtFixedRate({
            updateWeather()
            canvas.repaint(0, 130, canvas.size.width, 60)
        }, 0, 10, TimeUnit.MINUTES)
    }

    fun draw(g: Graphics) {
        g.color = Color.LIGHT_GRAY
        g.drawImage(icon.image, 20, 125, 64, 64, null)
        g.drawString("$temp ℉ ($wind mph)", 92, 169)
    }

    private fun updateWeather() {
        val info = currentWeather()

        val conditions = resolveWeatherImage(info?.conditions)
        icon = ImageIcon(ClockCanvas::class.java.getResource("/$conditions"))
        temp = info?.temperature ?: 0.0
        wind = info?.winds ?: 0.0
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