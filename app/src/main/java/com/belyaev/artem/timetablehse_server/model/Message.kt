package com.belyaev.artem.timetablehse_server.model

import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

class Message (
    var id: Int = 1,
    var content: String? = "",
    var userID: Int? = 1,
    var userName: String? = "",
    var date: Date = Date(),
    private var stringDate: String = ""
)
{
    constructor(jsonObject: JSONObject) : this() {
        id = 1
        content = jsonObject["content"] as String?
        userID = jsonObject["user_id"] as Int?
        userName = jsonObject["username"] as String?
        stringDate = jsonObject["date"] as String
        date = parseDate(stringDate)

    }

    private fun parseDate(stringDate: String): Date{
        return SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX", Locale.getDefault()).parse(stringDate)
    }
}