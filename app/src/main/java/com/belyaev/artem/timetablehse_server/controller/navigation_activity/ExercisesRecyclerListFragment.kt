package com.belyaev.artem.timetablehse_server.controller.navigation_activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import com.belyaev.artem.timetablehse_server.R

import com.belyaev.artem.timetablehse_server.adapter.ExercisesRecyclerAdapter
import com.belyaev.artem.timetablehse_server.controller.teacher_tab_activity.TeacherTabActivity
import com.belyaev.artem.timetablehse_server.model.Exercise
import com.belyaev.artem.timetablehse_server.model.ExerciseRealm
import com.belyaev.artem.timetablehse_server.model.Group
import com.belyaev.artem.timetablehse_server.utils.ApiTimeTable
import com.belyaev.artem.timetablehse_server.utils.Constants
import com.belyaev.artem.timetablehse_server.utils.ExerciseCallType
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.realm.Realm
import io.realm.RealmList
import io.realm.RealmQuery
import io.realm.RealmResults
import io.realm.kotlin.where
import kotlinx.android.synthetic.main.fragment_exercises.*
import kotlin.collections.ArrayList

class ExercisesRecyclerListFragment : Fragment()  {

    var exerciseCallType: ExerciseCallType = ExerciseCallType.BY_GROUP

    private lateinit var mExercisesAdapter: ExercisesRecyclerAdapter
    private lateinit var mLayoutManager: LinearLayoutManager
    private lateinit var mMainView: View
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mSpinner: Spinner
    private var mSpinnerGroups: ArrayList<Group>? = null
    private var mGroupID = -1
    private var isSection: Boolean = false

    private val lastVisibleItemPositiom: Int
        get() = mLayoutManager.findLastVisibleItemPosition()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        mMainView = inflater.inflate(R.layout.fragment_exercises , container, false)

        mLayoutManager = LinearLayoutManager(activity)
        mRecyclerView = mMainView.findViewById(R.id.recyclerView)
        mRecyclerView.layoutManager = mLayoutManager

        //loadWithRealm()

        when (exerciseCallType){
            ExerciseCallType.BY_GROUP -> getExercisesByGroup()
            ExerciseCallType.BY_TEACHER -> callWebService(exerciseCallType, 0)
        }

        Log.d("FUN","ExercisesRecyclerListFragment.onCreateView")

        setRecyclerViewScrollListener()

        return mMainView
    }

    @SuppressLint("CheckResult")
    private fun loadWithRealm() {
        val realm = Realm.getDefaultInstance()
        val exercisesRealm: RealmResults<ExerciseRealm>? = realm.where(ExerciseRealm::class.java).findAll()
        val list: ArrayList<Exercise> = arrayListOf()
        for (i in exercisesRealm!!){
            var exercise = Exercise()
            exercise.id = i.id
            exercise.date = i.date
            exercise.lesson =i.lesson
            exercise.teacher = i.teacher
            exercise.time = i.time
            list.add(exercise)
        }
        updateUI(list)

        val apiTimeTable = ApiTimeTable.getApi()
        val call = apiTimeTable.getExercisesRealmByGroupID(1)
        call
            .subscribeOn(Schedulers.io())
            .unsubscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe ({
                if (it.exercises != null){
                    val exercises = it.exercises!!
                    for (i in 0 until exercises.size){
                        val exercise = exercises[i]
                        realm.beginTransaction()
                        realm.copyToRealmOrUpdate(exercise)
                        realm.commitTransaction()
                    }
                }

            },{

            })

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

    private fun getExercisesByGroup(){
        val sharedPreferences = activity?.getSharedPreferences(Constants.PREFS_FILENAME.value, 0)
        if (sharedPreferences != null) {
            mGroupID = sharedPreferences.getInt("group_id", -1)
        }

        if (mGroupID == -1){
            mSpinner = mMainView.findViewById(R.id.spinner_group_ex)
            mSpinner.onItemSelectedListener = spinnerItemSelected
            getAdapter()
        } else {
            callWebService(exerciseCallType, mGroupID)
        }
    }

    private fun callWebService(exerciseCallType: ExerciseCallType, groupID: Int){

        val apiTimeTable = ApiTimeTable.getApi()

        when (exerciseCallType) {
            ExerciseCallType.BY_GROUP -> {
                val call = apiTimeTable.getExercisesByGroupID(groupID)
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



    @SuppressLint("CheckResult")
    fun getAdapter() {
        val apiTimeTable = ApiTimeTable.getApi()
        val call = apiTimeTable.getGroups()
        call
            .subscribeOn(Schedulers.io())
            .unsubscribeOn(Schedulers.computation())
            .observeOn(Schedulers.io())
            .subscribe ({
                if (it.groups != null) {
                    mSpinnerGroups = it.groups
                    val items = mutableListOf<String>()
                    for (i in 0 until it.groups.size) {
                        items.add(it.groups[i].name)
                    }
                    mGroupID = it.groups[0].id
                    callWebService(exerciseCallType, mGroupID)
                    activity?.runOnUiThread{
                        mSpinner.adapter = ArrayAdapter(activity, android.R.layout.simple_spinner_dropdown_item, items)
                        mSpinner.visibility = View.VISIBLE
                    }
                } else {
                    Log.d("ERROR", "Groups load error")
                }

            }, {
                Toast.makeText(activity?.applicationContext, it.message, Toast.LENGTH_SHORT).show()
            })
    }

    private val spinnerItemSelected: AdapterView.OnItemSelectedListener = object: AdapterView.OnItemSelectedListener {
        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            val groups = mSpinnerGroups ?: return
            mGroupID = groups[position].id
            callWebService(exerciseCallType, mGroupID)
        }
        override fun onNothingSelected(parent: AdapterView<*>?) {
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
