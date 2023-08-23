package com.example.newscenter.ui.view

import LocationPermissionDialog
import android.location.Location
import android.widget.Toast
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.newscenter.utils.LocationUtils
import com.example.newscenter.R
import com.example.newscenter.utils.WeatherIcon
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

val weatherUtils = com.example.newscenter.utils.WeatherUtils()

@Composable
fun WeatherView() {
    var temperature by remember { mutableStateOf("--") }
    val defaultIcon = WeatherIcon(R.drawable.weather_sunny, Color(0xFFFFD54F))
    var weatherIcon by remember { mutableStateOf(defaultIcon) }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        scope.launch(Dispatchers.IO) {
            val ip = weatherUtils.getIp()
            val temp = weatherUtils.getWeather(ip)
            temperature = temp.first
            weatherIcon = temp.second
        }
    }
    // 如果有定位
    var userLocation: String
    LocationPermissionDialog(
        onAllSuccess = {
            LocationUtils.getInstance(context)!!.getLocation(object : LocationUtils.LocationCallBack {
                override fun setLocation(location: Location?) {
                    if (location != null) {
                        userLocation = "${location.latitude},${location.longitude}"
                        scope.launch(Dispatchers.IO) {
                            val temp = weatherUtils.getWeather(userLocation)
                            temperature = temp.first
                            weatherIcon = temp.second
                        }
                        Toast.makeText(context, userLocation, Toast.LENGTH_SHORT).show()
                    }
                }
            })
        }
    )
    Row(modifier = Modifier.padding(horizontal = 12.dp)) {
        Text(text = "$temperature °C", color = MaterialTheme.colorScheme.onSurface)
        Spacer(modifier = Modifier.size(8.dp))
        Icon(
            painter = painterResource(id = weatherIcon.icon),
            modifier = Modifier.size(24.dp),
            contentDescription = "WeatherView",
            tint = weatherIcon.color
        )
    }
}