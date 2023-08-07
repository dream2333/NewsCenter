package com.example.newscenter.ui.view

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.newscenter.R

data class Weather(
    val icon : Int,
    val color : Color,
)

val weathers = listOf(
    Weather(R.drawable.weather_sunny, Color(0xFFFFD54F)),
)

@Composable
fun WeatherView() {
    Row(modifier = Modifier.padding(horizontal = 12.dp)) {
        Text(text = "${23}°C")
        Spacer(modifier = Modifier.size(8.dp))
        Icon(
            painter = painterResource(id = weathers[0].icon),
            modifier = Modifier.size(24.dp),
            contentDescription = "WeatherView",
            tint = weathers[0].color
        )
    }


}