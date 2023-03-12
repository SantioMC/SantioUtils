package me.santio.utils.nms.types.player

import me.santio.utils.reflection.reflection
import org.bukkit.entity.Player

class CraftPlayerNMS(private val player: Player, private val craftPlayer: Any) {

    fun get() = craftPlayer
    fun player() = player
    fun handle() = EntityPlayerNMS(craftPlayer.reflection().method("getHandle", false)!!.invoke()!!)
    fun gameProfile() = handle().gameProfile()

}