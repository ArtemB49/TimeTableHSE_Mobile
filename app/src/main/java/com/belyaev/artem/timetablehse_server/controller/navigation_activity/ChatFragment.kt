package com.belyaev.artem.timetablehse_server.controller.navigation_activity

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.belyaev.artem.timetablehse_server.R
import com.belyaev.artem.timetablehse_server.adapter.ChatAdapter
import com.belyaev.artem.timetablehse_server.adapter.TeacherRecyclerViewAdapter
import com.belyaev.artem.timetablehse_server.model.Message
import com.belyaev.artem.timetablehse_server.model.Teacher

class ChatFragment : Fragment() {

    private lateinit var mMainView: View
    private lateinit var mLayoutManager: LinearLayoutManager
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mMessagesAdapter: ChatAdapter

    private val messageListPlaceholder = arrayListOf<Message>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        mMainView = inflater.inflate(R.layout.fragment_chat, container, false)

        val message1 = Message()
        message1.content = "hi"
        message1.userName = "Artem"
        message1.userID = 1

        val message2 = Message()
        message2.content = "hello"
        message2.userName = "tos"
        message2.userID = 3

        messageListPlaceholder.add(message1)
        messageListPlaceholder.add(message2)
        messageListPlaceholder.add(message1)
        messageListPlaceholder.add(message2)

        mLayoutManager = LinearLayoutManager(activity)
        mRecyclerView = mMainView.findViewById(R.id.recyclerView)
        mRecyclerView.layoutManager = mLayoutManager
        mMessagesAdapter = ChatAdapter(messageListPlaceholder)
        mRecyclerView.adapter = mMessagesAdapter

        return mMainView
    }

}