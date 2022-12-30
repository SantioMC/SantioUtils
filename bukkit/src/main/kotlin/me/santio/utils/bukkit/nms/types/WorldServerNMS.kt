package me.santio.utils.bukkit.nms.types

import me.santio.utils.reflection.reflection

class WorldServerNMS(private val worldServer: Any) {

    fun get() = worldServer
    fun worldData() = WorldDataNMS(worldServer.reflection().field("worldData", false)!!.value()!!)
    fun dimension() = worldServer.reflection().field("dimension", false)!!.value() as Byte

}