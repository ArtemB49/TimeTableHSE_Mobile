package com.belyaev.artem.timetablehse_server.controller.navigation_activity

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.Editable
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
import android.widget.Button
import com.belyaev.artem.timetablehse_server.R
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_chat.*
import org.json.JSONArray
import java.util.*
import kotlin.collections.ArrayList


class ChatFragment : Fragment() {

    private lateinit var mMainView: View
    private lateinit var mLayoutManager: LinearLayoutManager
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mMessagesAdapter: ChatAdapter
    private val mMessages: MutableList<Message> = mutableListOf()
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
        mSocket.on("message", onMessage)
        mSocket.connect()

        val sendButton = mMainView.findViewById<Button>(R.id.button_chatbox_send)

        sendButton.setOnClickListener(onClickSendMessage)

        mLayoutManager = LinearLayoutManager(activity)
        mRecyclerView = mMainView.findViewById(R.id.recyclerView)
        mRecyclerView.layoutManager = mLayoutManager
        mMessagesAdapter = ChatAdapter(mMessages)
        mRecyclerView.adapter = mMessagesAdapter

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
            for (i in 0..messagesJson.length()-1) {
                val message = Message(messagesJson[i] as JSONObject)
                mMessages.add(message)
            }
            mMessagesAdapter.notifyDataSetChanged()
        }
    }

    private val onMessage: Emitter.Listener = Emitter.Listener {
        activity?.runOnUiThread {
            val messageJson = it[0] as JSONObject
            val message = Message(messageJson)
            mMessages.add(message)
            mMessagesAdapter.notifyDataSetChanged()
        }
    }

    private fun sendMessage(message: Message){
        mSocket = MainApplication.mSocket
        mSocket?.emit("msg", JSONObject("" +
                "{" +
                    "username: \"${message.userName}\"," +
                    "user_id: ${message.userID}," +
                    "content: \"${message.content}\"" +
                "}"))
    }

    private val onClickSendMessage = View.OnClickListener {
        val message = Message(1, edittext_chatbox.text.toString(), 1, "Artem", Date())
        edittext_chatbox.text.clear()
        sendMessage(message)
    }
}


