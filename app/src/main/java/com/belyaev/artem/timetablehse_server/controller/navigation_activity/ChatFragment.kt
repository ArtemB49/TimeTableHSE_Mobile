package com.belyaev.artem.timetablehse_server.controller.navigation_activity

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.belyaev.artem.timetablehse_server.MainApplication
import com.belyaev.artem.timetablehse_server.R
import com.belyaev.artem.timetablehse_server.adapter.ChatAdapter
import com.belyaev.artem.timetablehse_server.model.Message
import com.belyaev.artem.timetablehse_server.utils.Constants
import io.socket.client.Socket
import io.socket.emitter.Emitter
import kotlinx.android.synthetic.main.fragment_chat.*
import org.json.JSONArray
import org.json.JSONObject
import java.util.*


class ChatFragment : Fragment() {

    private lateinit var mMainView: View
    private lateinit var mLayoutManager: LinearLayoutManager
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mMessagesAdapter: ChatAdapter
    private val mMessages: MutableList<Message> = mutableListOf()
    private var mSocket: Socket? = null
    private var mUserID: Int = 0
    private var mGroupID: Int = 0
    private var isConnected = true
    private var isNeedHistory = true



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        mMainView = inflater.inflate(R.layout.fragment_chat, container, false)

        val sharedPreferences = activity?.getSharedPreferences(Constants.PREFS_FILENAME.value, 0)
        mUserID = sharedPreferences?.getInt("user_id", 1)!!
        mGroupID = sharedPreferences?.getInt("group_id", 1)!!

        val mSocket = (MainApplication.mSocket) ?: return null


        mSocket.on(Socket.EVENT_CONNECT, onConnect)
        mSocket.on("history", onHistory)
        mSocket.on("message", onMessage)
        mSocket.connect()

        val sendButton = mMainView.findViewById<Button>(R.id.button_chatbox_send)

        sendButton.setOnClickListener(onClickSendMessage)

        mLayoutManager = LinearLayoutManager(activity)
        mRecyclerView = mMainView.findViewById(R.id.recyclerView)
        mRecyclerView.layoutManager = mLayoutManager
        mMessagesAdapter = ChatAdapter(mMessages, mUserID)
        mRecyclerView.adapter = mMessagesAdapter

        return mMainView
    }

    private fun getHistory(){
        if (isNeedHistory){
            mSocket = MainApplication.mSocket
            mSocket?.emit("receiveHistory", mGroupID)
            isNeedHistory = false
        }
    }

    private fun enterRoom(){

        mSocket = MainApplication.mSocket
        mSocket?.emit("enterRoom", mGroupID)
    }


    private val onConnect: Emitter.Listener = Emitter.Listener {
        activity?.run {
            if (isConnected){
                getHistory()
                enterRoom()
            }
        }
    }

    private val onHistory: Emitter.Listener = Emitter.Listener {
        activity?.run {
            val messagesJson = it[0] as JSONArray
            for (i in 0..messagesJson.length()-1) {
                val message = Message(messagesJson[i] as JSONObject)
                mMessages.add(message)
            }
            mMessages.sortBy{selector(it)}
            activity?.runOnUiThread {
                mMessagesAdapter.notifyDataSetChanged()
            }

        }
    }

    private val onMessage: Emitter.Listener = Emitter.Listener {
        activity?.run {
            val messageJson = it[0] as JSONObject
            val message = Message(messageJson)
            activity?.runOnUiThread {
                mMessagesAdapter.addItem(message)
                mRecyclerView.scrollToPosition(mMessagesAdapter.itemCount - 1)
            }
        }
    }

    private fun sendMessage(message: Message){
        mSocket = MainApplication.mSocket
        mSocket?.emit(
            "msg",
            mGroupID,
            JSONObject("" +
                "{" +
                    "email: \"${message.email}\"," +
                    "user_id: ${message.userID}," +
                    "content: \"${message.content}\"" +
                "}"))
    }

    private val onClickSendMessage = View.OnClickListener {

        val message = Message(1, edittext_chatbox.text.toString(), mUserID, "Artem@ya.ru", Date())
        edittext_chatbox.text.clear()
        sendMessage(message)
    }

    fun selector(message: Message): Date = message.date

    override fun onDetach() {
        super.onDetach()
        val mSocket = MainApplication.mSocket
        mSocket?.disconnect()
    }
}


