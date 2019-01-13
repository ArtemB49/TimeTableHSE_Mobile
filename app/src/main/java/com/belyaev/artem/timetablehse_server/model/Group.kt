package com.belyaev.artem.timetablehse_server.model

import io.realm.RealmObject
import java.util.*

open class Group (
    open var id: Int = 0,
    open var name: String = "",
    open var dateAdition: Date = Date(),
    open var countStudents: Int = 0


) : RealmObject()