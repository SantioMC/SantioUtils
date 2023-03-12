package me.santio.utils.nms.types.player

import me.santio.utils.reflection.reflection

class EntityPlayerNMS(private val handle: Any) {

    fun get() = handle
    fun gameProfile() = GameProfileNMS(handle.reflection().method("getProfile", false)!!.invoke()!!)
    fun connection() = PlayerConnectionNMS(handle.reflection().field(false, "b", "playerConnection")!!.value()!!)

}