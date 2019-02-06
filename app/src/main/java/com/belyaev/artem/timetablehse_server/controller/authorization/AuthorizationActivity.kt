package com.belyaev.artem.timetablehse_server.controller.authorization

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.belyaev.artem.timetablehse_server.R
import com.belyaev.artem.timetablehse_server.utils.Constants
import okhttp3.*
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException

class AuthorizationActivity : AppCompatActivity() {

    private val JSON = MediaType.parse("application/json; charset=utf-8")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_authorization)

        val client = OkHttpClient()

        val formBody = FormBody.Builder()
            .add("username", "admin")
            .add("password", "a")
            .build()
        val request = Request.Builder()
            .url(Constants.SERVICE_HOST.value + "login")
            .post(formBody)
            .build()


        println(Constants.SERVICE_HOST.value + "login")

        client.newCall(request).enqueue(object: Callback{

            override fun onResponse(call: Call, response: Response) {
                val responseData = response.body().toString()
                try {
                    val json = JSONObject(responseData)
                    println("Auth request successful!")
                    println(json)
                } catch (e: JSONException){
                    e.printStackTrace()
                }

            }

            override fun onFailure(call: Call, e: IOException) {
                println("Auth request onFailure!")
            }

        })

    }
}
