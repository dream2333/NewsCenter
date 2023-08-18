package com.example.newscenter.ui.view

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.newscenter.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

data class Weather(
    val icon: Int,
    val color: Color,
)


@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun WeatherView() {
    var temperture by remember { mutableStateOf("--") }
    val defaultIcon = Weather(R.drawable.weather_sunny, Color(0xFFFFD54F))
    var weatherIcon by remember { mutableStateOf(defaultIcon) }
    CoroutineScope(Dispatchers.IO).launch {
        val weather = com.example.newscenter.spider.Weather()
        val ip = weather.getIp()
        val temp = weather.getWeather(ip)
        temperture = temp.first
        val code = temp.second
        weatherIcon = when (code) {
            "1000" -> {
                Weather(R.drawable.weather_sunny, Color(0xFFFFD54F))
            }

            "1003" -> {
                Weather(R.drawable.weaather_cloudy, Color(0xFFC2C2C2))
            }

            "1183" -> {
                Weather(R.drawable.weather_rainy, Color(0xFF82ADE2))
            }
            else -> {
                Weather(R.drawable.weather_sunny, Color(0xFFFFD54F))
            }
        }
    }


    Row(modifier = Modifier.padding(horizontal = 12.dp)) {
        Text(text = "$temperture °C", color = MaterialTheme.colorScheme.onSurface)
        Spacer(modifier = Modifier.size(8.dp))
        Icon(
            painter = painterResource(id = weatherIcon.icon),
            modifier = Modifier.size(24.dp),
            contentDescription = "WeatherView",
            tint = weatherIcon.color
        )
    }


}