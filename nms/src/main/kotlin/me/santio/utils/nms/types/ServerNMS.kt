package me.santio.utils.nms.types

import me.santio.utils.nms.NMSClasses
import org.bukkit.Server

@Suppress("unused")
class ServerNMS(private val server: Server, private val nmsServer: Any) {

    fun get() = nmsServer
    fun server() = server

    fun connection(): Any {
        return ServerConnectionNMS(
            server,
            NMSClasses.Minecraft.SERVER
                .findField(NMSClasses.Minecraft.SERVER_CONNECTION.get(), 0)!!
                .value(nmsServer)!!
        )
    }

}