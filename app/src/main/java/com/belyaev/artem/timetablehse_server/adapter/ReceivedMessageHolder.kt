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
        val dateFormat = SimpleDateFormat("yyyyMMdd", Locale.FRANCE)
        val stringDate = when (dateFormat.format(message.date).equals(dateFormat.format(Date()))){
            true -> SimpleDateFormat("HH:mm", Locale.FRANCE).format(message.date)
            false -> SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.FRANCE).format(message.date)
        }

        mainView.text_message_time.text = stringDate

        mainView.text_message_body.text = message.content
        mainView.text_message_name.text = message.userName


    }

}