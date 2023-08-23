package com.example.newscenter.utils

import androidx.compose.ui.graphics.Color
import com.example.newscenter.R
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException
import java.nio.charset.Charset

class WeatherUtils {
    private val client = OkHttpClient()
    fun getIp():String{
        val url = "https://ip.useragentinfo.com/json"
        val request = Request.Builder()
            .url(url)
            .build()

        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) throw IOException("Unexpected code $response")
            val b = response.body!!.bytes()
            val pattern = "\"ip\": \"(.*?)\",".toRegex()
            val text = String(b, Charset.forName("utf-8"))
            return pattern.find(text)?.groupValues?.get(1)!!
        }
    }
    fun getWeather(queryStr:String):Pair<String,WeatherIcon> {
        val api = "http://api.weatherapi.com/v1/current.json?key=f5a89dc68c0645b58aa70038231808&q=$queryStr&aqi=no"
        val request = Request.Builder()
            .url(api)
            .build()

        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) throw IOException("Unexpected code $response")
            val b = response.body!!.bytes()
            val patternTemp = "\"temp_c\":(.*?),".toRegex()
            val patternCode = "\"code\":(.*?),".toRegex()
            val text = String(b, Charset.forName("utf-8"))
            val temperature = patternTemp.find(text)?.groupValues?.get(1)!!
            val code = patternCode.find(text)?.groupValues?.get(1)!!
            val icon = getWeatherIcon(code)
            return Pair(temperature,icon)
        }
    }

    fun getWeatherIcon(code:String):WeatherIcon{
        return when (code) {
            "1000" -> {
                WeatherIcon(R.drawable.weather_sunny, Color(0xFFFFD54F))
            }

            "1003" -> {
                WeatherIcon(R.drawable.weaather_cloudy, Color(0xFFC2C2C2))
            }

            "1183" -> {
                WeatherIcon(R.drawable.weather_rainy, Color(0xFF82ADE2))
            }
            else -> {
                WeatherIcon(R.drawable.weather_sunny, Color(0xFFFFD54F))
            }
        }
    }
}

data class WeatherIcon(
    val icon: Int,
    val color: Color,
)


