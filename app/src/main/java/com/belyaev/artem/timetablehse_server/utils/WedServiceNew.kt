package com.belyaev.artem.timetablehse_server.utils

import okhttp3.*
import java.io.IOException


class WedServiceNew(val url: HttpUrl) {


    private val client = OkHttpClient()


    fun run() {
        val request = Request.Builder()
            .url(url)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }


            override fun onResponse(call: Call, response: Response) {

                print(response.toString())
            }

        })
    }
}