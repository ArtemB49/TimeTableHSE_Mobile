package com.belyaev.artem.timetablehse_server.controller

import android.app.ListActivity
import android.os.AsyncTask
import android.os.Bundle
import okhttp3.OkHttpClient
import okhttp3.Request
import android.util.Log
import android.os.StrictMode
import android.widget.ListView
import com.belyaev.artem.timetablehse_server.adapter.ClassiesAdapter
import com.belyaev.artem.timetablehse_server.R
import com.belyaev.artem.timetablehse_server.model.ClassParcelable
import org.json.JSONArray
import org.json.JSONObject


class MainActivity : ListActivity() {

    val client: OkHttpClient = OkHttpClient()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (android.os.Build.VERSION.SDK_INT > 9) {
            val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
            StrictMode.setThreadPolicy(policy)
        }



        val okHttpHandler = OkHttpHandler()
        okHttpHandler.execute("http://192.168.100.9:1515/api/classies/1")


    }

    fun updateUI(classies: List<ClassParcelable>){
        val listView = findViewById<ListView>(android.R.id.list)
        val adapter = ClassiesAdapter(this, classies)
        listView.adapter = adapter
    }

    inner class OkHttpHandler: AsyncTask<String, Void, List<ClassParcelable>?>(){

        private val client = OkHttpClient()

        override fun onPostExecute(result: List<ClassParcelable>?) {
            if (result == null) {
                Log.d("RESPONSE", "NO result")
            } else {
                updateUI(result)
            }

        }

        override fun doInBackground(vararg params: String?): List<ClassParcelable>? {
            val builder = Request.Builder()
            builder.url(params[0])
            val request = builder.build()

            try {
                val response = client.newCall(request).execute()
                val JSON = JSONObject(response.body()!!.string())

                val classiesList: MutableList<ClassParcelable> = mutableListOf()

                val classiesJSONArray: JSONArray = JSON["classies"] as JSONArray
                for (i in 0..(classiesJSONArray.length()-1)) {
                    val classItem = classiesJSONArray.getJSONObject(i)
                    classiesList.add(ClassParcelable(classItem))
                }

                return classiesList
            } catch (e: Exception) {
                e.printStackTrace()
            }

            return null
        }


    }

}
