package com.belyaev.artem.timetablehse_server.model

import com.google.gson.annotations.SerializedName
import io.realm.RealmObject
import java.util.*

open class Group (
    open var id: Int = 0,
    open var name: String = "",
    @SerializedName("year_admission")
    open var dateAdition: Date = Date(),
    @SerializedName("students_count")
    open var countStudents: Int = 0


) : RealmObject()