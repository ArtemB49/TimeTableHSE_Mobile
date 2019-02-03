package com.belyaev.artem.timetablehse_server.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import com.belyaev.artem.timetablehse_server.model.Message
import kotlinx.android.synthetic.main.item_message_received.view.*
import java.text.SimpleDateFormat
import java.util.*

class ReceivedMessageHolder (itemView: View):
    RecyclerView.ViewHolder(itemView),
    View.OnClickListener{

    private var mainView: View = itemView

    init {
        itemView.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        Log.d("ITEM", "onClick")
    }

    fun bindData(message: Message){


        val date = message.date ?: Date()


        val stringDate = when (date > Date()){
            true -> SimpleDateFormat("dd.MM.yyyy HH:mm:ss", Locale.FRANCE).format(date)
            false -> SimpleDateFormat("HH:mm:ss", Locale.FRANCE).format(date)
        }

        mainView.text_message_time.text = stringDate

        mainView.text_message_body.text = message.content
        mainView.text_message_name.text = message.userName


    }

}