package com.belyaev.artem.timetablehse_server.model

import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

class Message (
    var id: Int,
    var content: String?,
    var userID: Int?,
    var userName: String?,
    var date: Date
)
{
    constructor(jsonObject: JSONObject) : this(
        id = 1,
        content = jsonObject["content"] as String,
        userID = jsonObject["user_id"] as Int,
        userName = jsonObject["username"] as String,
        date =  SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX", Locale.getDefault()).parse(jsonObject["date"] as String)
    )

    private fun parseDate(stringDate: String): Date{
        return SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX", Locale.getDefault()).parse(stringDate)
    }
}