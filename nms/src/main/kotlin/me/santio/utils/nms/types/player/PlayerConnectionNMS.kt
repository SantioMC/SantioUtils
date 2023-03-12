package me.santio.utils.nms.types.player

import me.santio.utils.reflection.reflection

class PlayerConnectionNMS(private val connection: Any) {

    fun get() = connection
    fun sendPacket(packet: Any) = connection.reflection().method(false, "sendPacket", "a")!!.invoke(packet)

}