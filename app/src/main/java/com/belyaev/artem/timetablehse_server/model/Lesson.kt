package com.belyaev.artem.timetablehse_server.model

import io.realm.RealmObject

open class Lesson (
    open var id: Int = 0,
    open var name: String = ""

) : RealmObject()