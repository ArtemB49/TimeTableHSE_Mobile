package com.belyaev.artem.timetablehse_server.model

import android.os.Parcel
import android.os.Parcelable
import org.json.JSONObject

data class TeacherParcelable(
    val id: Int,
    val name: String

) : Parcelable {

    constructor(teacher: Teacher)
            :this(
        id = teacher.id,
        name = teacher.name
    )

    constructor(jsonObject: JSONObject)
            :this(
        id = jsonObject["id"] as Int,
        name = jsonObject["name"] as String
    )

    constructor(parcel: Parcel)
            :this(
        id = parcel.readInt(),
        name = parcel.readString()
    )

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeInt(id)
        dest?.writeString(name)
    }

    companion object {
        @JvmField
        val CREATOR = object : Parcelable.Creator<TeacherParcelable>{
            override fun createFromParcel(source: Parcel): TeacherParcelable? = TeacherParcelable(source)
            override fun newArray(size: Int): Array<TeacherParcelable?> = arrayOfNulls(size)
        }
    }


}