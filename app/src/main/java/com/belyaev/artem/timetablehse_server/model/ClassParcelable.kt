package com.belyaev.artem.timetablehse_server.model

import android.os.Parcel
import android.os.Parcelable
import org.json.JSONArray
import org.json.JSONObject


data class ClassParcelable(
    val id: Int,
    val lesson: String,
    val teacher: String,
    val date: String,
    val time: String

) : Parcelable {



    constructor(jsonObject: JSONObject)
            :this(
        id = jsonObject["id"] as Int,
        lesson = jsonObject["lesson"] as String,
        teacher = jsonObject["teacher"] as String,
        date = jsonObject["date"] as String,
        time = jsonObject["time"] as String
    )

    constructor(exercise: Exercise)
            :this(
        id = exercise.id,
        lesson = exercise.lesson!!,
        teacher = exercise.teacher!!,
        date = exercise.date!!,
        time = exercise.time!!
    )

    constructor(parcel: Parcel)
            :this(
        id = parcel.readInt(),
        lesson = parcel.readString(),
        teacher = parcel.readString(),
        date = parcel.readString(),
        time = parcel.readString()
    )

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeInt(id)
        dest?.writeString(lesson)
        dest?.writeString(teacher)
        dest?.writeString(date)
        dest?.writeString(time)
    }

    companion object {
        @JvmField
        val CREATOR = object : Parcelable.Creator<ClassParcelable>{
            override fun createFromParcel(source: Parcel): ClassParcelable? = ClassParcelable(source)
            override fun newArray(size: Int): Array<ClassParcelable?> = arrayOfNulls(size)
        }
    }


}