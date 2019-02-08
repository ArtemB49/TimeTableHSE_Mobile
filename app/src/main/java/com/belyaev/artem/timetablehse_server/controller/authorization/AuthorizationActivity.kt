package com.belyaev.artem.timetablehse_server.controller.authorization

import android.annotation.SuppressLint
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.belyaev.artem.timetablehse_server.R
import com.belyaev.artem.timetablehse_server.model.Login
import com.belyaev.artem.timetablehse_server.model.LoginResponse
import com.belyaev.artem.timetablehse_server.utils.ApiTimeTable
import com.belyaev.artem.timetablehse_server.utils.Constants
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.*
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException

class AuthorizationActivity : AppCompatActivity() {

    private val JSON = MediaType.parse("application/json; charset=utf-8")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_authorization)

        logout()
        loginByClient()
    }

    private fun logout(){
        val client = OkHttpClient()

        val formBody = FormBody.Builder()
            .add("username", "admin")
            .add("password", "a")
            .build()
        val request = Request.Builder()
            .url(Constants.SERVICE_HOST.value + "logout")
            .post(formBody)
            .build()




        client.newCall(request).enqueue(object: Callback{

            override fun onResponse(call: Call, response: Response) {
                val responseData = response.body().toString()


            }

            override fun onFailure(call: Call, e: IOException) {
                println("Auth request onFailure!")
            }

        })
    }

    @SuppressLint("CheckResult")
    private fun loginByRetrofit(){

        val apiTimeTable = ApiTimeTable.getApi()

        val call = apiTimeTable.login(Login("admin", "a"))

        call
            .subscribeOn(Schedulers.io())
            .unsubscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ response: LoginResponse? ->
                println(response?.token)
                println(response?.user_id)

            }, {
                println(it.message)
            })

    }


    private fun loginByClient(){
        val client = OkHttpClient()

        val formBody = FormBody.Builder()
            .add("username", "admin")
            .add("password", "a")
            .build()
        val request = Request.Builder()
            .url(Constants.SERVICE_HOST.value)
            .get()
            .build()




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
