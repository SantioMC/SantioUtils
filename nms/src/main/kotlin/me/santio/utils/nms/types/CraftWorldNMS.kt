package me.santio.utils.nms.types

import me.santio.utils.reflection.reflection
import org.bukkit.World

@Suppress("unused")
class CraftWorldNMS(private val world: World, private val craftWorld: Any) {

    fun get() = craftWorld
    fun world() = world
    fun handle() = WorldServerNMS(craftWorld.reflection().method("getHandle", false)!!.invoke()!!)

}