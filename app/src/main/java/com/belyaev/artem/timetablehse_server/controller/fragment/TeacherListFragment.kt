package com.belyaev.artem.timetablehse_server.controller.fragment

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.belyaev.artem.timetablehse_server.R
import com.belyaev.artem.timetablehse_server.WebService
import com.belyaev.artem.timetablehse_server.adapter.TeacherRecyclerViewAdapter

import com.belyaev.artem.timetablehse_server.controller.fragment.dummy.DummyContent.DummyItem
import com.belyaev.artem.timetablehse_server.model.ClassParcelable
import com.belyaev.artem.timetablehse_server.model.Teacher
import com.belyaev.artem.timetablehse_server.model.TeacherParcelable
import java.util.ArrayList

/**
 * A fragment representing a list of Items.
 * Activities containing this fragment MUST implement the
 * [TeacherListFragment.OnListFragmentInteractionListener] interface.
 */
class TeacherListFragment : Fragment() {

    // TODO: Customize parameters
    private var columnCount = 1

    private var listener: OnListFragmentInteractionListener? = null

    private val BROADCAST_ID = "com.artem.timetable"
    private lateinit var mMainView: View
    private lateinit var mLayoutManager: LinearLayoutManager
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mTeachersAdapter: TeacherRecyclerViewAdapter

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        activity?.registerReceiver(broadcastReceiver, IntentFilter(BROADCAST_ID))
        Log.d("FUN","TeacherListFragment.onActivityCreated")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        mMainView = inflater.inflate(R.layout.fragment_teacher_list, container, false)

        mLayoutManager = LinearLayoutManager(activity)
        mRecyclerView = mMainView.findViewById(R.id.recyclerView)
        mRecyclerView.layoutManager = mLayoutManager

        Log.d("FUN","TeacherListFragment.onCreateView")
        initList {
            if (mMainView.context != null){

                updateUI(it)
            }
        }

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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training name
     * [Communicating with Other Fragments](http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information.
     */
    interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onListFragmentInteraction(item: TeacherParcelable?)
    }

    private val broadcastReceiver = object : BroadcastReceiver(){
        override fun onReceive(context: Context?, intent: Intent?) {
            Log.d("FUN","TeacherListFragment.broadcastReceiver.onReceive")
            val teachersList = intent?.getParcelableArrayListExtra<TeacherParcelable>("list")


            if (teachersList != null) {
                updateUI(teachersList)
            } else {

            }
        }
    }

    private fun initList(completion: (ArrayList<TeacherParcelable>) -> Unit) {


        requestToAsy()

    }

    private fun updateUI(list: ArrayList<TeacherParcelable>){

        Log.d("FUN","TeacherListFragment.updateUI")
        mTeachersAdapter = TeacherRecyclerViewAdapter(list, listener)

        activity?.runOnUiThread{
            mRecyclerView.adapter = mTeachersAdapter
        }

    }

    private fun requestToAsy(){

        val intent = Intent(activity,  WebService::class.java)
        intent.putExtra("url", "http://192.168.100.9:1515/api/teachers")
        intent.putExtra("type", 2)
        activity?.startService(intent)

    }
}
