package com.belyaev.artem.timetablehse_server.controller.teacher_tab_activity

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.belyaev.artem.timetablehse_server.R
import kotlinx.android.synthetic.main.fragment_teacher_tab.view.*

class TeacherInfoFragment: Fragment() {

    private lateinit var mMainView: View
    private lateinit var mActivity: TeacherTabActivity

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        mActivity = activity as TeacherTabActivity
        mMainView = inflater.inflate(R.layout.fragment_teacher_tab, container, false)
        mMainView.teacher_name.text = mActivity.mTeacher.name


        return mMainView
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
        fun newInstance(sectionNumber: Int): TeacherInfoFragment {
            val fragment = TeacherInfoFragment()
            val args = Bundle()
            args.putInt(ARG_SECTION_NUMBER, sectionNumber)
            fragment.arguments = args
            return fragment
        }
    }
}
