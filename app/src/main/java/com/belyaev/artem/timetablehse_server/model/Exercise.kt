package com.belyaev.artem.timetablehse_server.model

import io.realm.RealmObject
import java.util.*


open class Exercise(
    open var id: Int = 0,
    open var lesson: Lesson = Lesson(),
    open var teacher: Teacher = Teacher(),
    open var date: Date = Date(),
    open var time: String = ""

) : RealmObject()





