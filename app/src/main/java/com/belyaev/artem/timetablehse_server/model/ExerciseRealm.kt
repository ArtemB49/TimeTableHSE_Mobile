package com.belyaev.artem.timetablehse_server.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass
import java.util.*

@RealmClass
open class ExerciseRealm : RealmObject(){
    @PrimaryKey
    @SerializedName("id")
    @Expose
    open var id: Int = 0

    @SerializedName("lesson")
    @Expose
    open var lesson: String? = null

    @SerializedName("teacher")
    @Expose
    open var teacher: String? = null

    @SerializedName("date")
    @Expose
    open var date: String? = null

    @SerializedName("time")
    @Expose
    open var time: String? = null
}





