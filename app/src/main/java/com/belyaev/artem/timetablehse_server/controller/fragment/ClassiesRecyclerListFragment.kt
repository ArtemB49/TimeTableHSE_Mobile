package com.belyaev.artem.timetablehse_server.controller.fragment

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.belyaev.artem.timetablehse_server.R
import com.belyaev.artem.timetablehse_server.WebService
import com.belyaev.artem.timetablehse_server.adapter.ClassiesRecyclerAdapter
import com.belyaev.artem.timetablehse_server.model.ClassParcelable
import org.json.JSONArray
import org.json.JSONException
import java.util.ArrayList

class ClassiesRecyclerListFragment : Fragment()  {

    //private val asyDate = AsyDateFormatter.instance

    private val BROADCAST_ID = "com.artem.timetable"
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
        activity?.registerReceiver(broadcastReceiver, IntentFilter(BROADCAST_ID))
        Log.d("FUN","ClassiesRecyclerListFragment.onActivityCreated")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        mMainView = inflater.inflate(R.layout.classies_recycler_fragment, container, false)

        mLayoutManager = LinearLayoutManager(activity)
        mRecyclerView = mMainView.findViewById(R.id.recyclerView)
        mRecyclerView.layoutManager = mLayoutManager

        Log.d("FUN","ClassiesRecyclerListFragment.onCreateView")
        initList {
            if (mMainView.context != null){

                updateUI(it)
            }
        }

        setRecyclerViewScrollListener()


        return mMainView
    }

    private fun updateUI(list: ArrayList<ClassParcelable>){

        Log.d("FUN","ClassiesRecyclerListFragment.updateUI")
        mClassiesAdapter = ClassiesRecyclerAdapter(list)

        activity?.runOnUiThread{
            mRecyclerView.adapter = mClassiesAdapter
        }

    }

    private fun requestToAsy(){

        val intent = Intent(activity,  WebService::class.java)
        intent.putExtra("url", "http://192.168.100.9:1515/api/classies/1")
        intent.putExtra("type", 1)

        activity?.startService(intent)

    }

    private val broadcastReceiver = object : BroadcastReceiver(){
        override fun onReceive(context: Context?, intent: Intent?) {
            Log.d("FUN","ClassiesRecyclerListFragment.broadcastReceiver.onReceive")
            val classiesList = intent?.getParcelableArrayListExtra<ClassParcelable>("list")
            if (classiesList != null) {
                updateUI(classiesList)
            } else {

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


    private fun initList(completion: (ArrayList<ClassParcelable>) -> Unit) {


            requestToAsy()

    }




    private fun parse(value: String): JSONArray?{
        var result: JSONArray?= null
        try {
            result = JSONArray(value)

        } catch (e: JSONException){
            e.printStackTrace()
        }
        return result
    }

}
