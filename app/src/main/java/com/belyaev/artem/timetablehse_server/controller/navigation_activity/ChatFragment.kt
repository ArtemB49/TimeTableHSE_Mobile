package com.belyaev.artem.timetablehse_server.controller.navigation_activity

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.belyaev.artem.timetablehse_server.MainApplication
import com.belyaev.artem.timetablehse_server.adapter.ChatAdapter
import com.belyaev.artem.timetablehse_server.model.Message
import io.socket.client.Socket
import io.socket.emitter.Emitter
import org.json.JSONObject
import android.util.Log
import com.belyaev.artem.timetablehse_server.R
import com.google.gson.Gson
import org.json.JSONArray
import java.util.*
import kotlin.collections.ArrayList


class ChatFragment : Fragment() {

    private lateinit var mMainView: View
    private lateinit var mLayoutManager: LinearLayoutManager
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mMessagesAdapter: ChatAdapter
    private var mSocket: Socket? = null
    private var isConnected = true
    private var isNeedHistory = true

    private val messageListPlaceholder = arrayListOf<Message>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        mMainView = inflater.inflate(R.layout.fragment_chat, container, false)
        val mSocket = MainApplication.mSocket

        if (mSocket == null) return null
        mSocket.on(Socket.EVENT_CONNECT, onConnect)
        mSocket.on("history", onHistory)
        mSocket.connect()


        val message1 = Message(1, "hi", 1, "Artem", Date())


        val message2 = Message(1, "hello", 3, "tos", Date())


        messageListPlaceholder.add(message1)
        messageListPlaceholder.add(message2)
        messageListPlaceholder.add(message1)
        messageListPlaceholder.add(message2)

        mLayoutManager = LinearLayoutManager(activity)
        mRecyclerView = mMainView.findViewById(R.id.recyclerView)
        mRecyclerView.layoutManager = mLayoutManager

        return mMainView
    }

    private fun getHistory(){
        if (isNeedHistory){
            mSocket = MainApplication.mSocket
            mSocket?.emit("receiveHistory", 1)
            isNeedHistory = false
        }

    }


    private val onConnect: Emitter.Listener = Emitter.Listener {
        activity?.run {
            if (isConnected){
                getHistory()
            }
        }
    }

    private val onHistory: Emitter.Listener = Emitter.Listener {
        activity?.runOnUiThread {
            val messagesJson = it[0] as JSONArray
            val messages = ArrayList<Message>()
            for (i in 0..messagesJson.length()-1) {
                val message = Message(messagesJson[i] as JSONObject)
                messages.add(message)

            }
            updateUI(messages)
        }
    }

    private fun updateUI(list: ArrayList<Message>?){

        if (list != null && list.size != 0){
            mMessagesAdapter = ChatAdapter(list)

            activity?.runOnUiThread{
                mRecyclerView.adapter = mMessagesAdapter
            }
            //empty_view.visibility = View.GONE
            //recyclerView.visibility = View.VISIBLE
        } else {
            //empty_view.visibility = View.VISIBLE
            //recyclerView.visibility = View.GONE
        }
    }
}


