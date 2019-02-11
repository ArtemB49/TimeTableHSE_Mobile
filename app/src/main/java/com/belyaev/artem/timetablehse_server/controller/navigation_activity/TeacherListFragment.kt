package com.belyaev.artem.timetablehse_server.controller.navigation_activity

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.belyaev.artem.timetablehse_server.R
import com.belyaev.artem.timetablehse_server.adapter.TeacherRecyclerViewAdapter
import com.belyaev.artem.timetablehse_server.model.Teacher
import com.belyaev.artem.timetablehse_server.utils.ApiTimeTable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_exercises.*

/**
 * A fragment representing a list of Items.
 * Activities containing this fragment MUST implement the
 * [TeacherListFragment.OnListFragmentInteractionListener] interface.
 */
class TeacherListFragment : Fragment() {


    private var listener: OnListFragmentInteractionListener? = null

    private lateinit var mMainView: View
    private lateinit var mLayoutManager: LinearLayoutManager
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mTeachersAdapter: TeacherRecyclerViewAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        mMainView = inflater.inflate(R.layout.fragment_teacher_list, container, false)

        mLayoutManager = LinearLayoutManager(activity)
        mRecyclerView = mMainView.findViewById(R.id.recyclerView)
        mRecyclerView.layoutManager = mLayoutManager

        callWebService()

        return mMainView
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnListFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnListFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }


    interface OnListFragmentInteractionListener {
        fun onListFragmentInteraction(item: Teacher?)
    }

    private fun updateUI(list: ArrayList<Teacher>?){

        Log.d("FUN","TeacherListFragment.updateUI")

        if (list != null && list.size != 0){
            mTeachersAdapter = TeacherRecyclerViewAdapter(list, listener)

            activity?.runOnUiThread{
                mRecyclerView.adapter = mTeachersAdapter
            }
            empty_view.visibility = View.GONE
            recyclerView.visibility = View.VISIBLE
        } else {
            empty_view.visibility = View.VISIBLE
            recyclerView.visibility = View.GONE
        }

    }

    @SuppressLint("CheckResult")
    private fun callWebService() {

        val apiTimeTable = ApiTimeTable.getApi()
        val call = apiTimeTable.getTeachers()
        call
            .subscribeOn(Schedulers.io())
            .unsubscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({

                updateUI(it.teachers)

                Log.d("-------RESULT-------", "Successful")
            }, {
                Toast.makeText(activity?.applicationContext, it.message, Toast.LENGTH_SHORT).show()
            })
    }
}
