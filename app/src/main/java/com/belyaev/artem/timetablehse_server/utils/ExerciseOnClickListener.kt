package com.belyaev.artem.timetablehse_server.utils

import android.content.Intent
import android.view.View
import com.belyaev.artem.timetablehse_server.controller.ExerciseActivity
import com.belyaev.artem.timetablehse_server.model.ClassParcelable
import com.belyaev.artem.timetablehse_server.model.Exercise

class ExerciseOnClickListener(
    var exercises:  ArrayList<Exercise>,
    var position: Int
): View.OnClickListener {



    override fun onClick(view: View?) {

        val context =  view?.context
        val exerciseParcelable = ClassParcelable(exercises[position])
        val intent = Intent(context, ExerciseActivity::class.java)
        intent.putExtra("exercise", exerciseParcelable)
        context?.startActivity(intent)
    }
}