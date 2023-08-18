package com.example.newscenter.spider

import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException
import java.nio.charset.Charset

class Weather {
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
    fun getWeather(ip:String):Pair<String,String> {
        val api = "http://api.weatherapi.com/v1/current.json?key=f5a89dc68c0645b58aa70038231808&q=${ip}&aqi=no"
        val request = Request.Builder()
            .url(api)
            .build()

        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) throw IOException("Unexpected code $response")
            val b = response.body!!.bytes()
            val patternTemp = "\"temp_c\":(.*?),".toRegex()
            val patternCode = "\"code\":(.*?),".toRegex()
            val text = String(b, Charset.forName("utf-8"))
            val temperture = patternTemp.find(text)?.groupValues?.get(1)!!
            val code = patternCode.find(text)?.groupValues?.get(1)!!
            return Pair(temperture,code)
        }
    }
}
