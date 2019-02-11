package com.belyaev.artem.timetablehse_server.model

import android.annotation.SuppressLint
import com.google.gson.annotations.SerializedName

class User(
    @SerializedName("id")
    var userID: Int = 0,

    @SerializedName("firstname")
    var firstName: String = "",

    @SerializedName("lastname")
    var lastName: String = "",

    @SerializedName("thirdname")
    var thirdName: String = "",

    var email: String = "",

    @SerializedName("group_id")
    var groupID: Int = 0


)