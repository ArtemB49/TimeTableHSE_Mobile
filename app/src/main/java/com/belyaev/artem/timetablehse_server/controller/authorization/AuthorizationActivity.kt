package com.belyaev.artem.timetablehse_server.controller.authorization

import android.annotation.SuppressLint
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v4.app.ListFragment
import android.util.Log
import android.view.KeyEvent
import com.belyaev.artem.timetablehse_server.R
import com.belyaev.artem.timetablehse_server.controller.navigation_activity.NavigationActivity
import com.belyaev.artem.timetablehse_server.model.Login
import com.belyaev.artem.timetablehse_server.model.LoginResponse
import com.belyaev.artem.timetablehse_server.utils.ApiTimeTable
import com.belyaev.artem.timetablehse_server.utils.Constants
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_authorization.*
import kotlinx.android.synthetic.main.fragment_teacher.*
import okhttp3.*
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException

class AuthorizationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_authorization)

        loadFragment(LoginFragment())
    }

    fun loadFragment(fragment: Fragment?): Boolean{
        if (fragment != null){
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit()
            return true
        }
        return false
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

    override fun onBackPressed() {
        moveTaskToBack(false)
        setResult(201, Intent(this, NavigationActivity::class.java))
        finish()
    }

    fun successfulyRegistration(){
        Snackbar.make(root_layout, "Успешная регистрация", Snackbar.LENGTH_LONG).show()
        loadFragment(LoginFragment())
    }






}
