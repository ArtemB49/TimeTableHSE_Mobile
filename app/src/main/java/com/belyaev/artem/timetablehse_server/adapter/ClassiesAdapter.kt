package com.belyaev.artem.timetablehse_server.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.belyaev.artem.timetablehse_server.R
import com.belyaev.artem.timetablehse_server.model.ClassParcelable
import java.time.LocalDate
import java.util.*


class ClassiesAdapter(private val activity: Activity, classList: List<ClassParcelable>) : BaseAdapter() {

    private var classiesList = ArrayList<ClassParcelable>()

    init {
        this.classiesList = classList as ArrayList
    }

    override fun getCount(): Int {
        return classiesList.size
    }

    override fun getItem(i: Int): Any {
        return i
    }

    override fun getItemId(i: Int): Long {
        return i.toLong()
    }

    @SuppressLint("InflateParams", "ViewHolder")
    override fun getView(i: Int, convertView: View?, viewGroup: ViewGroup): View {
        val vi: View
        if (convertView == null) {
            val inflater = activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            vi = inflater.inflate(R.layout.class_row, viewGroup, false)
        } else {
            vi = convertView
        }
        val dateLabel: TextView = vi.findViewById(R.id.date)
        val timeLabel: TextView = vi.findViewById(R.id.time)
        val lessonLabel: TextView = vi.findViewById(R.id.lesson)
        val teacherLabel: TextView = vi.findViewById(R.id.teacher)

        //val date: LocalDate = LocalDate(classiesList[i].date)

        dateLabel.text = classiesList[i].date
        timeLabel.text = classiesList[i].time
        lessonLabel.text = classiesList[i].lesson
        teacherLabel.text = classiesList[i].teacher
        return vi
    }
}