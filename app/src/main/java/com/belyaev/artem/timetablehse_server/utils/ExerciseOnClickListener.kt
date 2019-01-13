package com.belyaev.artem.timetablehse_server.utils

import android.content.Intent
import android.view.View
import com.belyaev.artem.timetablehse_server.controller.ExerciseActivity
import com.belyaev.artem.timetablehse_server.model.ClassParcelable

class ExerciseOnClickListener(
    var exercises:  ArrayList<ClassParcelable>,
    var position: Int
): View.OnClickListener {



    override fun onClick(view: View?) {

        val context =  view?.context


        val intent = Intent(context, ExerciseActivity::class.java)
        intent.putExtra("exercise", exercises[position])
        context?.startActivity(intent)
    }
}