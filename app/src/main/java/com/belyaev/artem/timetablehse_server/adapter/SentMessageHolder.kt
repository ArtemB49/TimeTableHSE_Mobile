package com.belyaev.artem.timetablehse_server.adapter

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import com.belyaev.artem.timetablehse_server.model.Message
import kotlinx.android.synthetic.main.item_message_sent.view.*

class SentMessageHolder (itemView: View):
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

        //val date = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX", Locale.getDefault()).parse(message.time)
        //mainView.text_message_time.text = SimpleDateFormat("dd.MM.yyyy", Locale.FRANCE).format(date)


        mainView.text_message_body.text = message.content

    }

}