package com.belyaev.artem.timetablehse_server.adapter

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.belyaev.artem.timetablehse_server.R
import com.belyaev.artem.timetablehse_server.model.Message
import com.belyaev.artem.timetablehse_server.utils.extention.inflate


class ChatAdapter(private val messages: MutableList<Message>):
    RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    private val VIEW_TYPE_MESSAGE_SENT = 1
    private val VIEW_TYPE_MESSAGE_RECEIVED = 2


    private val userIDPlaceholder: Int = 1

    //private val mRealm = Realm.getDefaultInstance()
    private lateinit var mRecyclerView: RecyclerView

    override fun getItemViewType(position: Int): Int {
        val message = messages[position]

        return when (message.userID == userIDPlaceholder){
            true -> VIEW_TYPE_MESSAGE_SENT
            false -> VIEW_TYPE_MESSAGE_RECEIVED
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {


        return when (viewType) {
            VIEW_TYPE_MESSAGE_SENT -> {
                val inflatedView = parent.inflate(R.layout.item_message_sent, false)
                SentMessageHolder(inflatedView)
            }

            VIEW_TYPE_MESSAGE_RECEIVED -> {
                val inflatedView = parent.inflate(R.layout.item_message_received, false)
                ReceivedMessageHolder(inflatedView)
            }
            else -> {
                val inflatedView = parent.inflate(R.layout.item_message_received, false)
                ReceivedMessageHolder(inflatedView)
            }
        }
    }



    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {


        val message = messages[position]

        when (holder.itemViewType) {
            VIEW_TYPE_MESSAGE_SENT -> {
                (holder as SentMessageHolder).bindData(message)
            }

            VIEW_TYPE_MESSAGE_RECEIVED -> {
                (holder as ReceivedMessageHolder).bindData(message)
            }
            else -> {
                (holder as ReceivedMessageHolder).bindData(message)
            }
        }
    }



    override fun getItemCount() = messages.size


    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        mRecyclerView = recyclerView
    }

    fun addItem(message: Message){
        messages.add(message)
        notifyItemInserted(messages.size)
    }
}