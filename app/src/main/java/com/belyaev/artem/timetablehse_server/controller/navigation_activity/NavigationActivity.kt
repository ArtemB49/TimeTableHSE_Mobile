package com.belyaev.artem.timetablehse_server.controller.navigation_activity

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.MenuItem
import com.belyaev.artem.timetablehse_server.R
import com.belyaev.artem.timetablehse_server.controller.authorization.AuthorizationActivity
import com.belyaev.artem.timetablehse_server.controller.notification_service.NotificationService
import com.belyaev.artem.timetablehse_server.controller.teacher_tab_activity.TeacherTabActivity
import com.belyaev.artem.timetablehse_server.model.Teacher
import com.belyaev.artem.timetablehse_server.model.TeacherParcelable
import com.belyaev.artem.timetablehse_server.utils.Constants
import kotlinx.android.synthetic.main.activity_navigation.*

class NavigationActivity : AppCompatActivity() ,
    BottomNavigationView.OnNavigationItemSelectedListener, TeacherListFragment.OnListFragmentInteractionListener {

    private var mCurrentID: Int = R.id.navigation_timetable
    private var mTemptID: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigation)
        startNotificationService()
        navigation.setOnNavigationItemSelectedListener(this)
        loadFragment(ExercisesRecyclerListFragment())

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

        if (item.itemId == mCurrentID){
            return false
        }

        mCurrentID = item.itemId

        val fragment: Fragment? = when (item.itemId){
            R.id.navigation_timetable -> ExercisesRecyclerListFragment()
            R.id.navigation_teachers -> TeacherListFragment()
            R.id.navigation_chat -> chooseChat()
            else -> null
        }

        return loadFragment(fragment)
    }

    override fun onListFragmentInteraction(item: Teacher?) {
        val teacherParcelable = TeacherParcelable(item!!)
        val intent = Intent(this, TeacherTabActivity::class.java)
        intent.putExtra("teacher", teacherParcelable)
        startActivity(intent)
    }

    private fun chooseChat(): Fragment?{
        val sharedPreferences = getSharedPreferences(Constants.PREFS_FILENAME.value, 0)
        val userID = sharedPreferences.getInt("user_id", -1)


        return when (userID == -1){
            true -> {
                startActivityForResult(Intent(this, AuthorizationActivity::class.java),1)
                mCurrentID = R.id.navigation_timetable
                null
            }
            false -> ChatFragment()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        mTemptID = when(resultCode) {
            200 ->  R.id.navigation_chat
            else -> R.id.navigation_timetable
        }
    }

    override fun onResume() {
        super.onResume()
        navigation.selectedItemId = mTemptID
    }

    private fun startNotificationService(){
        startService(Intent(applicationContext, NotificationService::class.java))
    }
}
