package me.santio.utils.nms.types

import org.bukkit.Server

@Suppress("unused")
class ServerConnectionNMS(private val server: Server, private val serverConnection: Any) {

    fun get() = serverConnection
    fun server() = server

}