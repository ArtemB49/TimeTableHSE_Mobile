package com.belyaev.artem.timetablehse_server.controller

import android.annotation.SuppressLint
import android.app.ListActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.belyaev.artem.timetablehse_server.R
import com.belyaev.artem.timetablehse_server.utils.ApiTimeTable
import com.belyaev.artem.timetablehse_server.utils.Constants
import com.belyaev.artem.timetablehse_server.utils.ExerciseCallType
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient


class MainActivity : ListActivity() {

    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val callType = intent.getIntExtra(Constants.EXERCISE_CALL_TYPE.value, ExerciseCallType.BY_GROUP.value)
        callWebService(2)


    }

    private fun callWebService(callType: Int){

        val apiTimeTable = ApiTimeTable.getApi()

        when (callType) {
            1 -> {
                val call = apiTimeTable.getExercisesByGroupID()
                call
                    .subscribeOn(Schedulers.io())
                    .unsubscribeOn(Schedulers.computation())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe ({

                        val classies = it.classies
                        val lesson = if (classies != null) classies[0].lesson else "no result"


                        Log.d("-------RESULT-------", lesson)
                    }, {
                        Toast.makeText(applicationContext, it.message, Toast.LENGTH_SHORT).show()
                    })
            }

            2 -> {
                val call = apiTimeTable.getExercisesByTeacherID(1)
                call
                    .subscribeOn(Schedulers.io())
                    .unsubscribeOn(Schedulers.computation())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe ({

                        val classies = it.classies
                        val lesson = if (classies != null) classies[0].lesson else "no result"


                        Log.d("-------RESULT-------", lesson)
                    }, {
                        Toast.makeText(applicationContext, it.message, Toast.LENGTH_SHORT).show()
                    })
            }
        }





    }

}






