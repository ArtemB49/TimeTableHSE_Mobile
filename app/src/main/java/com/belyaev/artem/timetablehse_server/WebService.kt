package com.belyaev.artem.timetablehse_server

import android.annotation.SuppressLint
import android.app.Service
import android.content.Intent
import android.os.AsyncTask
import android.os.IBinder
import android.util.Log
import com.belyaev.artem.timetablehse_server.model.ClassParcelable
import com.belyaev.artem.timetablehse_server.model.TeacherParcelable
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONArray
import org.json.JSONObject
import kotlin.collections.ArrayList

class WebService : Service() {

    // WEB SETTINGS

    //private var SERVICE_LOGIN = ServiceConstants.LOGIN.value
    //private var SERVICE_PASSWORD = ServiceConstants.PASSWORD.value
    //private var TEST_SERVICE_PASSWORD = ServiceConstants.TEST_PASSWORD.value
    //private var SERVICE_URL = ServiceConstants.SERVICE_URL.value
    //private var TEST_SERVICE_URL = ServiceConstants.TEST_SERVICE_URL.value
    //private val PARAM_RESULT = "result"
    private val BROADCAST_ID = "com.artem.timetable"
    private val TURNOVER = "TURNOVER"
    private val OPERATION = "OPERATION"
    private val DOCS = "DOCS"
    private val STATION = "STATION"
    private var mType: Int? = null

    //private val mAsyService = AsyService.instance


    override fun onBind(intent: Intent): IBinder? {
        throw UnsupportedOperationException("Not yet implemented")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        Log.d("FUN","WebService.onStartCommand")

        mType = intent?.getIntExtra("type", 1)

        BackgroundTaskAsync().execute(intent)

        return super.onStartCommand(intent, flags, startId)
    }


    private fun answerFromServer(result: String){

        Log.d("FUN","WebService.answerFromServer")

        val resultIntent = Intent(BROADCAST_ID)
        val JSON = JSONObject(result)


        when (mType) {
            1 -> {
                Log.d("FUN","WebService.answerFromServer.classiesList")
                Log.d("FUN",result)

                val classiesList: ArrayList<ClassParcelable> = arrayListOf()

                val classiesJSONArray: JSONArray = JSON["classies"] as JSONArray
                for (i in 0..(classiesJSONArray.length()-1)) {
                    val classItem = classiesJSONArray.getJSONObject(i)
                    classiesList.add(ClassParcelable(classItem))
                }
                resultIntent.putParcelableArrayListExtra("EXERCISES_LIST", classiesList)
            }

            2 -> {
                Log.d("FUN","WebService.answerFromServer.teacherList")
                Log.d("FUN",result)
                val teacherList: ArrayList<TeacherParcelable> = arrayListOf()

                val teacherJSONArray: JSONArray = JSON["teachers"] as JSONArray
                for (i in 0..(teacherJSONArray.length()-1)) {
                    val teacherItem = teacherJSONArray.getJSONObject(i)
                    teacherList.add(TeacherParcelable(teacherItem))
                }
                resultIntent.putParcelableArrayListExtra("TEACHERS_LIST", teacherList)
            }
        }

        sendBroadcast(resultIntent)


    }


    @SuppressLint("StaticFieldLeak")
    inner class BackgroundTaskAsync: AsyncTask<Intent, Void, String>() {

        private val client = OkHttpClient()


        override fun onPostExecute(result: String?) {

            Log.d("FUN","WebService.onPostExecute")

            if (result == null) {
                Log.d("RESPONSE", "NO result")
            } else {
                answerFromServer(result)

            }

        }

        override fun doInBackground(vararg intents: Intent?): String? {

            Log.d("FUN","WebService.doInBackground")

            val intent = intents[0]

            if (intents[0] == null) {
                return ""
            }

            val url = intent?.getStringExtra("url")


            val builder = Request.Builder()
            builder.url(url!!)
            val request = builder.build()

            try {
                val response = client.newCall(request).execute()


                return response.body()!!.string()
            } catch (e: Exception) {
                e.printStackTrace()
            }



            return null
        }


    }

}