package com.belyaev.artem.timetablehse_server.controller.authorization

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.belyaev.artem.timetablehse_server.R
import com.belyaev.artem.timetablehse_server.controller.navigation_activity.NavigationActivity
import com.belyaev.artem.timetablehse_server.model.*
import com.belyaev.artem.timetablehse_server.model.response.UserResponse
import com.belyaev.artem.timetablehse_server.utils.ApiTimeTable
import com.belyaev.artem.timetablehse_server.utils.Constants
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment: Fragment() {

    private lateinit var mMainView: View
    private lateinit var mActivity: AuthorizationActivity

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        mActivity = activity as AuthorizationActivity
        mMainView = inflater.inflate(R.layout.fragment_login, container, false)

        val loginButton: Button = mMainView.findViewById(R.id.button_login)
        loginButton.setOnClickListener(loginClickListener)

        val registerButton: Button = mMainView.findViewById(R.id.button_reg)
        registerButton.setOnClickListener(registerClickListener)

        return mMainView
    }


    private val loginClickListener: View.OnClickListener = View.OnClickListener {
        val username = edt_username.text.toString()
        val password = edt_password.text.toString()
        login(username, password)
    }

    private val registerClickListener: View.OnClickListener = View.OnClickListener {
        mActivity.loadFragment(RegisterFragment())
    }

    @SuppressLint("CheckResult")
    private fun login(username: String, password: String){

        val apiTimeTable = ApiTimeTable.getApi()

        val call = apiTimeTable.login(Login(username, password))

        call
            .subscribeOn(Schedulers.io())
            .unsubscribeOn(Schedulers.computation())
            .observeOn(Schedulers.io())
            .subscribe({ response: LoginResponse? ->

                if (response?.user_id != null){
                    getUserData(response.user_id)
                }
            }, {
                showFailureText()
            })

    }

    // Показать текст если ошибка авторизации
    private fun showFailureText(){
        failure_text.visibility = View.VISIBLE
    }

    @SuppressLint("CheckResult")
    private fun getUserData(userID: Int) {
        val apiTimeTable = ApiTimeTable.getApi()

        val call = apiTimeTable.getUser(userID)

        call
            .subscribeOn(Schedulers.io())
            .unsubscribeOn(Schedulers.computation())
            .observeOn(Schedulers.io())
            .subscribe({ response: UserResponse? ->

                if (response?.user != null) {
                    saveUserData(response.user)
                }
            }, {

            })
    }

    private fun saveUserData(user: User){
        val intent = Intent(mActivity, NavigationActivity::class.java)
        val sharedPreferences = mActivity.getSharedPreferences(Constants.PREFS_FILENAME.value, 0)
        sharedPreferences.edit()
            .putInt("user_id", user.userID)
            .putString("fname", user.firstName)
            .putString("lname", user.lastName)
            .putString("tname", user.thirdName)
            .putString("email", user.email)
            .putInt("group_id", user.groupID)
            .apply()

        mActivity.runOnUiThread {
            mActivity.setResult(200, intent)
            mActivity.finish()
        }

    }

}