package com.belyaev.artem.timetablehse_server.controller.authorization

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.belyaev.artem.timetablehse_server.R
import com.belyaev.artem.timetablehse_server.controller.navigation_activity.NavigationActivity
import com.belyaev.artem.timetablehse_server.model.*
import com.belyaev.artem.timetablehse_server.utils.ApiTimeTable
import com.belyaev.artem.timetablehse_server.utils.Constants
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_register.*

class RegisterFragment: Fragment() {

    private lateinit var mMainView: View
    private lateinit var mActivity: AuthorizationActivity
    private lateinit var mSpinner: Spinner

    private var groupID: Int = 1
    private var spinnerGroups: ArrayList<Group>? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        mActivity = activity as AuthorizationActivity
        mMainView = inflater.inflate(R.layout.fragment_register, container, false)

        val loginButton: Button = mMainView.findViewById(R.id.button_login)
        loginButton.setOnClickListener(loginClickListener)

        val registerButton: Button = mMainView.findViewById(R.id.button_reg)
        registerButton.setOnClickListener(registerClickListener)

        mSpinner = mMainView.findViewById(R.id.spinner_group)
        mSpinner.onItemSelectedListener = spinnerItemSelected
        getAdapter()

        return mMainView
    }

    private val loginClickListener: View.OnClickListener = View.OnClickListener {
        mActivity.loadFragment(LoginFragment())
    }


    private val registerClickListener: View.OnClickListener = View.OnClickListener {
        val email = edt_email.text.toString()
        val password = mMainView.findViewById<EditText>(R.id.edt_password).text.toString()
        val firstName = edt_firstname.text.toString()
        val lastName = edt_lastname.text.toString()
        val thirdName = edt_thirdname.text.toString()
        register(Register(email, firstName, lastName, thirdName, password, 2, groupID))
    }

    @SuppressLint("CheckResult")
    private fun register(register: Register){
        val apiTimeTable = ApiTimeTable.getApi()

        val call = apiTimeTable.register(register)

        call
            .subscribeOn(Schedulers.io())
            .unsubscribeOn(Schedulers.computation())
            .observeOn(Schedulers.io())
            .subscribe({ response: RegisterResponse? ->

                if (response?.status != null && response.status == 200){
                    mActivity.successfulyRegistration()
                }
            }, {

            })
    }

    @SuppressLint("CheckResult")
    fun getAdapter() {
        val apiTimeTable = ApiTimeTable.getApi()
        val call = apiTimeTable.getGroups()
        call
            .subscribeOn(Schedulers.io())
            .unsubscribeOn(Schedulers.computation())
            .observeOn(Schedulers.io())
            .subscribe ({
                if (it.groups != null) {
                    spinnerGroups = it.groups
                    val items = mutableListOf<String>()
                    for (i in 0 until it.groups.size) {
                        items.add(it.groups[i].name)
                    }
                    mActivity.runOnUiThread{
                        mSpinner.adapter = ArrayAdapter(mActivity, android.R.layout.simple_spinner_dropdown_item, items)
                    }
                } else {
                    Log.d("ERROR", "Groups load error")
                }

            }, {
                Toast.makeText(activity?.applicationContext, it.message, Toast.LENGTH_SHORT).show()
            })
    }

    private val spinnerItemSelected: AdapterView.OnItemSelectedListener = object: AdapterView.OnItemSelectedListener{
        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            val groups = spinnerGroups ?: return
            groupID = groups[position].id
        }

        override fun onNothingSelected(parent: AdapterView<*>?) {

        }
    }

}