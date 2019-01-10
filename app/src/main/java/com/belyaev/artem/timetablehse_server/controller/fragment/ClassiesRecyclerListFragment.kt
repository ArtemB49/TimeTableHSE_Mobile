package com.belyaev.artem.timetablehse_server.controller.fragment

import android.content.IntentFilter
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.belyaev.artem.timetablehse_server.R
import com.belyaev.artem.timetablehse_server.adapter.ClassiesRecyclerAdapter
import com.belyaev.artem.timetablehse_server.model.ClassParcelable
import java.util.ArrayList

class ClassiesRecyclerListFragment : Fragment()  {

    //private val asyDate = AsyDateFormatter.instance


    private val purchaseList: ArrayList<ClassParcelable> = ArrayList()
    private var purchaseCount = 0
    //private val mRealm = Realm.getDefaultInstance()
    //private val mAsyService = AsyService.instance
    private lateinit var mClassiesAdapter: ClassiesRecyclerAdapter
    private lateinit var mLayoutManager: LinearLayoutManager
    private lateinit var mMainView: View
    private lateinit var mRecyclerView: RecyclerView

    private val lastVisibleItemPositiom: Int
        get() = mLayoutManager.findLastVisibleItemPosition()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //activity?.registerReceiver(broadcastReceiver, IntentFilter(BROADCAST_ID))
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        mMainView = inflater.inflate(R.layout.classies_recycler_fragment, container, false)

        mLayoutManager = LinearLayoutManager(activity)
        mRecyclerView = mMainView.findViewById(R.id.recyclerView)
        mRecyclerView.layoutManager = mLayoutManager

        /*
        initList {
            if (mMainView.context != null){

                updateUI(it)
            }
        }

        setRecyclerViewScrollListener()
        */

        return mMainView
    }
}