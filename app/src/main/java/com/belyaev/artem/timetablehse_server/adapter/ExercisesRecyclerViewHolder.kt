package com.belyaev.artem.timetablehse_server.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.belyaev.artem.timetablehse_server.R
import com.belyaev.artem.timetablehse_server.model.ClassParcelable
import com.belyaev.artem.timetablehse_server.model.Exercise
import kotlinx.android.synthetic.main.class_row.view.*
import kotlinx.android.synthetic.main.list_item_classies.view.*
import java.text.SimpleDateFormat
import java.util.*

class ExercisesRecyclerViewHolder (itemView: View):
    RecyclerView.ViewHolder(itemView),
    View.OnClickListener{

    var mainView: View = itemView
    private val classParcelable: ClassParcelable? = null
    private lateinit var mContext: Context
    var moreInformationView: LinearLayout
    var baseSumTextView: TextView


    init {
        itemView.setOnClickListener(this)
        mContext = itemView.context
        moreInformationView = itemView.findViewById(R.id.ll_more_info_purchase)
        baseSumTextView = itemView.findViewById(R.id.item_base_lesson)
    }

    override fun onClick(v: View?) {
        Log.d("ITEM", "onClick")

    }

    fun bindData(exercise: Exercise){

        val date = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX", Locale.getDefault()).parse(exercise.date)

        mainView.item_tv_date.text = SimpleDateFormat("dd.MM.yyyy", Locale.FRANCE).format(date)
        mainView.item_tv_time.text = exercise.time
        mainView.item_base_lesson.text = exercise.lesson
        mainView.item_extra_lesson.text = exercise.lesson
        mainView.item_tv_teacher.text = exercise.teacher

    }

}