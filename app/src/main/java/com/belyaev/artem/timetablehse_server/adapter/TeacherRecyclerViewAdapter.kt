package com.belyaev.artem.timetablehse_server.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.belyaev.artem.timetablehse_server.R


import com.belyaev.artem.timetablehse_server.controller.navigation_activity.TeacherListFragment.OnListFragmentInteractionListener
import com.belyaev.artem.timetablehse_server.controller.fragment.dummy.DummyContent.DummyItem
import com.belyaev.artem.timetablehse_server.model.TeacherParcelable

import kotlinx.android.synthetic.main.fragment_teacher.view.*

/**
 * [RecyclerView.Adapter] that can display a [DummyItem] and makes a call to the
 * specified [OnListFragmentInteractionListener].
 * TODO: Replace the implementation with code for your data type.
 */
class TeacherRecyclerViewAdapter(
    private val mValues: ArrayList<TeacherParcelable>,
    private val mListener: OnListFragmentInteractionListener?
) : RecyclerView.Adapter<TeacherRecyclerViewAdapter.ViewHolder>() {

    private val mOnClickListener: View.OnClickListener

    init {
        mOnClickListener = View.OnClickListener { v ->
            val item = v.tag as TeacherParcelable
            // Notify the active callbacks interface (the activity, if the fragment is attached to
            // one) that an item has been selected.
            mListener?.onListFragmentInteraction(item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_teacher, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mValues[position]
        holder.mContentView.text = item.name

        with(holder.mView) {
            tag = item
            setOnClickListener(mOnClickListener)
        }
    }

    override fun getItemCount(): Int = mValues.size

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {

        val mContentView: TextView = mView.content

        override fun toString(): String {
            return super.toString() + " '" + mContentView.text + "'"
        }
    }
}
