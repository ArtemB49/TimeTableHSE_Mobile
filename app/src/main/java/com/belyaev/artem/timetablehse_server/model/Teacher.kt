package com.belyaev.artem.timetablehse_server.model

import io.realm.RealmObject
import java.util.*

open class Teacher (
    open var id: Int = 0,
    open var name: String = ""

) : RealmObject()