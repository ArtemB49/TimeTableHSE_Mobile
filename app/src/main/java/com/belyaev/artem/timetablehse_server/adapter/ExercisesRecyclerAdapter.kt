package com.belyaev.artem.timetablehse_server.adapter

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.belyaev.artem.timetablehse_server.R
import com.belyaev.artem.timetablehse_server.model.Exercise
import com.belyaev.artem.timetablehse_server.utils.ExerciseOnClickListener
import com.belyaev.artem.timetablehse_server.utils.extention.inflate


class ExercisesRecyclerAdapter(private val exercises: ArrayList<Exercise>):
    RecyclerView.Adapter<ExercisesRecyclerViewHolder>(){

    private var mChangePosition: Int = 0
    private var mPreviousChangePosition: Int = -1

    //private val mRealm = Realm.getDefaultInstance()
    private lateinit var mRecyclerView: RecyclerView

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExercisesRecyclerViewHolder {

        val inflatedView = parent.inflate(R.layout.list_item_classies, false)


        return ExercisesRecyclerViewHolder(inflatedView)

    }

    override fun onBindViewHolder(holder: ExercisesRecyclerViewHolder, position: Int) {


        val exercise = exercises[position]

        val onClickListener = ExerciseOnClickListener(exercises, position)

        holder.bindData(exercise)


        holder.mainView.setOnClickListener(onClickListener)

    }



    override fun getItemCount() = exercises.size


    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        mRecyclerView = recyclerView
    }

}