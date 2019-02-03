package com.belyaev.artem.timetablehse_server.controller.chat

import io.socket.emitter.Emitter

interface ChatDelegation {

    fun onConnect(array: Array<out Any>)
}