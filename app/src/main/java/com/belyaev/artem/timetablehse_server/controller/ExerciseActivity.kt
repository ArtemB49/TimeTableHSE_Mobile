package com.belyaev.artem.timetablehse_server.controller

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.belyaev.artem.timetablehse_server.R
import com.belyaev.artem.timetablehse_server.model.ClassParcelable
import kotlinx.android.synthetic.main.activity_classies.*
import kotlinx.android.synthetic.main.list_item_classies.view.*
import java.text.SimpleDateFormat
import java.util.*

class ExerciseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_classies)

        // Кнопка "Назад" в Toolbar
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val exercise = intent.getParcelableExtra<ClassParcelable>("exercise")

        val date = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX", Locale.getDefault()).parse(exercise.date)

        exercise_date.text = SimpleDateFormat("dd.MM.yyyy", Locale.FRANCE).format(date)
        exercise_lesson.text = exercise.lesson
        exercise_teacher.text = exercise.teacher
        exercise_time.text = exercise.time

    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        val id = item?.itemId
        if (id == android.R.id.home){
            finish()
        }


        return super.onOptionsItemSelected(item)
    }
}
