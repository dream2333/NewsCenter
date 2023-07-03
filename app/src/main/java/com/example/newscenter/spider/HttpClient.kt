package com.example.newscenter.spider

import okhttp3.Call
import okhttp3.Callback
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import java.io.IOException
import java.nio.charset.Charset


class HttpClient {
    private val client = OkHttpClient()

    fun get(url: String, params: Map<String, String>,charset:String): String {
        val request = Request.Builder()
            .url(url)
            .build()

        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) throw IOException("Unexpected code $response")
            val b = response.body!!.bytes()
            return String(b, Charset.forName(charset))
        }
    }

    fun post(url: String, postBody: String,type: MediaType): String {
        val jsonBody: RequestBody = postBody.toRequestBody(type)
        val request = Request.Builder()
            .url(url)
            .post(jsonBody)
            .build()
        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) throw IOException("Unexpected code $response")
            val b = response.body!!.bytes()
            return String(b, Charset.forName("GBK"))
        }
    }


    fun postAsync(url: String, postBody: String, type: MediaType) {
        val jsonBody: RequestBody = postBody.toRequestBody(type)
        val request = Request.Builder()
            .url(url)
            .post(jsonBody)
            .build()
        val call = client.newCall(request)

        call.enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response) {
                response.use {
                    if (!response.isSuccessful) throw IOException("Unexpected code $response")
                    println(response.body!!.string())
                }
            }
        })
    }

    fun getAsync(url: String, params: Map<String, String>) {
        val request = Request.Builder()
            .url(url)
            .get()
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response) {
                response.use {
                    if (!response.isSuccessful) throw IOException("Unexpected code $response")
                    for ((name, value) in response.headers) {
                        println("$name: $value")
                    }
                    println(response.body!!.string())
                }
            }
        })
    }

}