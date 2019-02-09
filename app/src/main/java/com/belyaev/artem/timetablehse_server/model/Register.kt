package com.belyaev.artem.timetablehse_server.model

class Register(
    val email: String,
    val firstname: String,
    val lastname: String,
    val thirdname: String,
    val password: String,
    val type: Int,
    val group_id: Int?
)