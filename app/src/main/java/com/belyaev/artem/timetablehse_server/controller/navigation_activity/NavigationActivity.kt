package com.belyaev.artem.timetablehse_server.controller.navigation_activity

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.MenuItem
import com.belyaev.artem.timetablehse_server.R
import com.belyaev.artem.timetablehse_server.controller.teacher_tab_activity.TeacherTabActivity
import com.belyaev.artem.timetablehse_server.model.Teacher
import com.belyaev.artem.timetablehse_server.model.TeacherParcelable
import kotlinx.android.synthetic.main.activity_navigation.*

class NavigationActivity : AppCompatActivity() ,
    BottomNavigationView.OnNavigationItemSelectedListener, TeacherListFragment.OnListFragmentInteractionListener {

    private var currentID: Int = R.id.navigation_timetable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigation)

        navigation.setOnNavigationItemSelectedListener(this)
        loadFragment(ExercisesRecyclerListFragment())
        Log.d("FUN", "NavigationActivity.onCreate")
        //AsyDateFormatter.instance
    }

    private fun loadFragment(fragment: Fragment?): Boolean{
        if (fragment != null){
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit()
            Log.d("FUN", "NavigationActivity.loadFragment.true")
            return true

        }

        return false

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        Log.d("FUN", "NavigationActivity.onNavigationItemSelected")

        if (item.itemId == currentID){
            return false
        }

        currentID = item.itemId

        var fragment: Fragment? = null

        when (item.itemId){
            R.id.navigation_timetable -> fragment =
                    ExercisesRecyclerListFragment()
            R.id.navigation_teachers -> fragment =
                    TeacherListFragment()
        }

        return loadFragment(fragment)
    }

    override fun onListFragmentInteraction(item: Teacher?) {
        val teacherParcelable = TeacherParcelable(item!!)
        val intent = Intent(this, TeacherTabActivity::class.java)
        intent.putExtra("teacher", teacherParcelable)
        startActivity(intent)
    }
}
