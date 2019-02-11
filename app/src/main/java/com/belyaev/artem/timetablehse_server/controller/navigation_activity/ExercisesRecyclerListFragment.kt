package com.belyaev.artem.timetablehse_server.controller.navigation_activity

import android.content.IntentFilter
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

import com.belyaev.artem.timetablehse_server.adapter.ExercisesRecyclerAdapter
import com.belyaev.artem.timetablehse_server.controller.teacher_tab_activity.TeacherTabActivity
import com.belyaev.artem.timetablehse_server.model.ClassParcelable
import com.belyaev.artem.timetablehse_server.model.Exercise
import com.belyaev.artem.timetablehse_server.utils.ApiTimeTable
import com.belyaev.artem.timetablehse_server.utils.Constants
import com.belyaev.artem.timetablehse_server.utils.ExerciseCallType
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.classies_recycler_fragment.*
import kotlin.collections.ArrayList

class ExercisesRecyclerListFragment : Fragment()  {

    var exerciseCallType: ExerciseCallType = ExerciseCallType.BY_GROUP

    private lateinit var mExercisesAdapter: ExercisesRecyclerAdapter
    private lateinit var mLayoutManager: LinearLayoutManager
    private lateinit var mMainView: View
    private lateinit var mRecyclerView: RecyclerView
    private var isSection: Boolean = false

    private val lastVisibleItemPositiom: Int
        get() = mLayoutManager.findLastVisibleItemPosition()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        mMainView = inflater.inflate(R.layout.classies_recycler_fragment , container, false)

        mLayoutManager = LinearLayoutManager(activity)
        mRecyclerView = mMainView.findViewById(R.id.recyclerView)
        mRecyclerView.layoutManager = mLayoutManager

        Log.d("FUN","ExercisesRecyclerListFragment.onCreateView")


        callWebService(exerciseCallType)
        setRecyclerViewScrollListener()


        return mMainView
    }

    private fun updateUI(list: ArrayList<Exercise>?){

        Log.d("FUN","ExercisesRecyclerListFragment.updateUI")

        if (list != null && list.size != 0){
            mExercisesAdapter = ExercisesRecyclerAdapter(list)

            activity?.runOnUiThread{
                mRecyclerView.adapter = mExercisesAdapter
            }
            empty_view.visibility = View.GONE
            recyclerView.visibility = View.VISIBLE
        } else {
            empty_view.visibility = View.VISIBLE
            recyclerView.visibility = View.GONE
        }
    }

    private fun callWebService(exerciseCallType: ExerciseCallType){

        val apiTimeTable = ApiTimeTable.getApi()

        when (exerciseCallType) {
            ExerciseCallType.BY_GROUP -> {
                val call = apiTimeTable.getExercisesByGroupID()
                call
                    .subscribeOn(Schedulers.io())
                    .unsubscribeOn(Schedulers.computation())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe ({

                        updateUI(it.classies)

                        Log.d("-------RESULT-------", "Successful")
                    }, {
                        Toast.makeText(activity?.applicationContext, it.message, Toast.LENGTH_SHORT).show()
                    })
            }

            ExerciseCallType.BY_TEACHER -> {
                val teacherID = (activity as TeacherTabActivity).mTeacher.id

                val call = apiTimeTable.getExercisesByTeacherID(teacherID)
                call
                    .subscribeOn(Schedulers.io())
                    .unsubscribeOn(Schedulers.computation())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe ({

                        updateUI(it.classies)

                        Log.d("-------RESULT-------", "Successful")
                    }, {
                        Toast.makeText(activity?.applicationContext, it.message, Toast.LENGTH_SHORT).show()
                    })
            }
        }





    }

    private fun setRecyclerViewScrollListener() {
        mRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                val totalItemCount = recyclerView!!.layoutManager.itemCount

            }
        })
    }



    companion object {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private val ARG_SECTION_NUMBER = "section_number"

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        fun newInstance(sectionNumber: Int): ExercisesRecyclerListFragment {
            val fragment = ExercisesRecyclerListFragment()
            val args = Bundle()
            args.putInt(ARG_SECTION_NUMBER, sectionNumber)
            fragment.exerciseCallType = ExerciseCallType.BY_TEACHER
            fragment.isSection = true
            fragment.arguments = args
            return fragment
        }
    }

}
