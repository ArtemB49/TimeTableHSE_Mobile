package com.belyaev.artem.timetablehse_server

import android.app.Application
import android.content.Context
import com.belyaev.artem.timetablehse_server.utils.Constants
import io.realm.Realm
import io.realm.RealmConfiguration
import io.socket.client.IO
import io.socket.client.Socket
import java.net.URISyntaxException

class MainApplication: Application() {

    init {
        instance = this
        mSocket = getSocket()




    }

    companion object {
        private var instance: MainApplication? = null

        fun applicationContext(): Context {
            return instance!!.applicationContext
        }

        var mSocket: Socket? = null

        private fun getSocket(): Socket {
            try {
                return IO.socket(Constants.SERVICE_HOST_IO.value)
            } catch (error: URISyntaxException){
                throw RuntimeException(error)
            }
        }
    }

    override fun onCreate() {
        super.onCreate()
        Realm.init(this)
        val realmConfig = RealmConfiguration.Builder()
            .deleteRealmIfMigrationNeeded()
            .build()
        Realm.setDefaultConfiguration(realmConfig)

    }
}