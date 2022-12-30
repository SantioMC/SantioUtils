@file:JvmName("NMS")

package me.santio.utils.bukkit.nms

import me.santio.utils.bukkit.nms.types.CraftWorldNMS
import me.santio.utils.reflection.Reflection
import me.santio.utils.reflection.reflection
import me.santio.utils.reflection.types.ClassReflection
import org.bukkit.Bukkit
import org.bukkit.World

@Suppress("MemberVisibilityCanBePrivate")
object NMS {

    @JvmStatic
    val serverVersion: String = Bukkit.getServer().reflection().pkg().name().replace(".", ",").split(",")[3]

    private fun getCraftBukkitClass(name: String): ClassReflection<*> {
        return Reflection.getClass("org.bukkit.craftbukkit.$serverVersion.$name")!!
    }

    private fun getNMSClass(name: String): ClassReflection<*> {
        return Reflection.getClass("net.minecraft.server.$serverVersion.$name")!!
    }

    @JvmStatic
    @JvmName("get")
    fun World.nms(): CraftWorldNMS {
        return CraftWorldNMS(this, getCraftBukkitClass("CraftWorld").cast(this))
    }

    @JvmStatic
    fun worldMap() = getNMSClass("WorldMap")

}