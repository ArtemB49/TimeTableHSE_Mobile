package com.belyaev.artem.timetablehse_server.controller.notification_service

import android.content.Intent
import android.os.IBinder
import android.support.v4.app.NotificationCompat
import android.support.v4.app.NotificationManagerCompat
import android.util.Log
import com.belyaev.artem.timetablehse_server.MainApplication
import com.belyaev.artem.timetablehse_server.controller.navigation_activity.NavigationActivity
import io.socket.client.Socket
import io.socket.emitter.Emitter
import org.json.JSONObject
import android.graphics.Color
import com.belyaev.artem.timetablehse_server.controller.MainActivity
import android.app.*
import android.content.Context
import com.belyaev.artem.timetablehse_server.R
import com.belyaev.artem.timetablehse_server.utils.Constants


class NotificationService: Service() {

    private lateinit var notificationManager: NotificationManagerCompat
    private var mSocket: Socket? = null
    private var mGroupID: Int = -1

    override fun onCreate() {
        notificationManager = NotificationManagerCompat.from(this)
        val mSocket = (MainApplication.mSocket) ?: return

        mSocket.on("notification", onNotification)
        mSocket.connect()
        Log.d("!!NotificationService!!", "START")


    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return Service.START_STICKY
    }


    private val onNotification: Emitter.Listener = Emitter.Listener {
        val data = it[0] as JSONObject
        val groupID = data.getInt("group_id")

        val sharedPreferences = getSharedPreferences(Constants.PREFS_FILENAME.value, 0)
        if (sharedPreferences != null){
            mGroupID = sharedPreferences.getInt("group_id", -1)
        }
        if (mGroupID == groupID){
            sendNotification("Расписание НИУ ВШЭ", "Изменения в расписании вашей группы")
        }


    }

    private fun sendNotification(title: String, text: String){
        val NOTIFICATION_ID = 234

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager


        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {


            val CHANNEL_ID = "my_channel_01"
            val name = "my_channel"
            val Description = "This is my channel"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val mChannel = NotificationChannel(CHANNEL_ID, name, importance)
            mChannel.description = Description
            mChannel.enableLights(true)
            mChannel.lightColor = Color.RED
            mChannel.enableVibration(true)
            mChannel.vibrationPattern = longArrayOf(100, 200, 300, 400, 500, 400, 300, 200, 400)
            mChannel.setShowBadge(false)
            notificationManager.createNotificationChannel(mChannel)
        }

        val builder = NotificationCompat.Builder(this, "my_channel_01")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(title)
            .setContentText(text)

        val resultIntent = Intent(this, NavigationActivity::class.java)
        val stackBuilder = TaskStackBuilder.create(this)
        stackBuilder.addParentStack(NavigationActivity::class.java)
        stackBuilder.addNextIntent(resultIntent)
        val resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT)

        builder.setContentIntent(resultPendingIntent)

        notificationManager.notify(NOTIFICATION_ID, builder.build())
    }

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }
}