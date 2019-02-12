package com.belyaev.artem.timetablehse_server.controller.teacher_tab_activity

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.belyaev.artem.timetablehse_server.R
import com.belyaev.artem.timetablehse_server.model.TeacherParcelable
import kotlinx.android.synthetic.main.activity_teacher_tab.*

class TeacherTabActivity : AppCompatActivity() {

    /**
     * The [android.support.v4.view.PagerAdapter] that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * [android.support.v4.app.FragmentStatePagerAdapter].
     */
    private var mTeacherPagerAdapter: TeacherTabPagesAdapter? = null
    lateinit var mTeacher: TeacherParcelable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_teacher_tab)

        setSupportActionBar(toolbar)
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mTeacherPagerAdapter = TeacherTabPagesAdapter(supportFragmentManager)

        // Кнопка "Назад" в Toolbar
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        mTeacher = intent.getParcelableExtra("teacher")


        // Set up the ViewPager with the sections adapter.
        val viewPager: ViewPager = findViewById(R.id.container)
        viewPager.adapter = mTeacherPagerAdapter
        val tabLayout = findViewById<TabLayout>(R.id.tabs)
        tabLayout.setupWithViewPager(viewPager)



    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_teacher_tab, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val itemID = item.itemId

        when (itemID) {

            android.R.id.home -> finish()
        }

        return super.onOptionsItemSelected(item)
    }
}
