package com.belyaev.artem.timetablehse_server.adapter

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.belyaev.artem.timetablehse_server.R
import com.belyaev.artem.timetablehse_server.model.ClassParcelable
import com.belyaev.artem.timetablehse_server.utils.ExerciseOnClickListener
import com.belyaev.artem.timetablehse_server.utils.extention.inflate


class ClassiesRecyclerAdapter(private val exercises: ArrayList<ClassParcelable>):
    RecyclerView.Adapter<ClassiesRecyclerViewHolder>(){

    private var mChangePosition: Int = 0
    private var mPreviousChangePosition: Int = -1

    //private val mRealm = Realm.getDefaultInstance()
    private lateinit var mRecyclerView: RecyclerView

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClassiesRecyclerViewHolder {

        val inflatedView = parent.inflate(R.layout.list_item_classies, false)


        return ClassiesRecyclerViewHolder(inflatedView)

    }

    override fun onBindViewHolder(holder: ClassiesRecyclerViewHolder, position: Int) {


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