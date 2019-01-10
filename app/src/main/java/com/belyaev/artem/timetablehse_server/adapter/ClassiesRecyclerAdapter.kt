package com.belyaev.artem.timetablehse_server.adapter

import android.support.v7.widget.RecyclerView
import android.transition.TransitionManager
import android.view.View
import android.view.ViewGroup
import com.belyaev.artem.timetablehse_server.R
import com.belyaev.artem.timetablehse_server.model.ClassParcelable
import com.belyaev.artem.timetablehse_server.utils.inflate


class ClassiesRecyclerAdapter(private val purchases: ArrayList<ClassParcelable>):
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

        val isChanced = position==mChangePosition
        val purchase = purchases[position]
        holder.bindData(purchase)
        if (isChanced){
            holder.moreInformationView.visibility = View.VISIBLE
            holder.baseSumTextView.visibility = View.GONE
            mPreviousChangePosition = position
        } else {
            holder.moreInformationView.visibility = View.GONE
            holder.baseSumTextView.visibility = View.VISIBLE
        }


        holder.mainView.isActivated = isChanced
        holder.mainView.setOnClickListener {
            mChangePosition = if (isChanced) -1 else position
            TransitionManager.beginDelayedTransition(mRecyclerView)
            notifyItemChanged(mPreviousChangePosition)
            notifyItemChanged(mChangePosition)

        }

    }



    override fun getItemCount() = purchases.size


    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        mRecyclerView = recyclerView
    }

}