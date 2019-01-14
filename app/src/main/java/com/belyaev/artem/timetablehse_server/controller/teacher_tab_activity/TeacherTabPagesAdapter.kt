package com.belyaev.artem.timetablehse_server.controller.teacher_tab_activity

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.belyaev.artem.timetablehse_server.controller.navigation_activity.ExercisesRecyclerListFragment

class TeacherTabPagesAdapter (fm: FragmentManager) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return when (position){
            0 ->  TeacherInfoFragment.newInstance(position)
            1 ->  ExercisesRecyclerListFragment.newInstance(position)
            else ->  TeacherInfoFragment.newInstance(position)
        }

    }

    override fun getCount(): Int {
        // Show 3 total pages.
        return 2
    }

    override fun getPageTitle(position: Int): CharSequence? {

        val title = when (position) {
            0 -> "Информация"
            1 -> "Занятия"
            else -> "BOB"
        }

        return title
    }
}