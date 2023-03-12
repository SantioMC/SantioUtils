@file:JvmName("NMS")

package me.santio.utils.nms

import me.santio.utils.nms.types.CraftWorldNMS
import me.santio.utils.nms.types.ServerNMS
import me.santio.utils.nms.types.player.CraftPlayerNMS
import me.santio.utils.reflection.Reflection
import me.santio.utils.reflection.reflection
import me.santio.utils.reflection.types.ClassReflection
import org.bukkit.Bukkit
import org.bukkit.Server
import org.bukkit.World
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

@Suppress("MemberVisibilityCanBePrivate")
object NMS {

    @JvmStatic
    val serverVersion: String = Bukkit.getServer().reflection().pkg().name().replace(".", ",").split(",")[3]

    @JvmStatic
    fun isVersionNewerThan(version: String): Boolean {
        val currentVersion = serverVersion.split("_")
        val versionToCompare = version.split(".")

        for (i in currentVersion.indices) {
            if (currentVersion[i].toInt() > versionToCompare[i].toInt()) return true
            if (currentVersion[i].toInt() < versionToCompare[i].toInt()) return false
        }

        return false
    }

    @JvmStatic
    fun isVersion(version: String): Boolean {
        val currentVersion = serverVersion.split("_")
        val versionToCompare = version.split(".")

        for (i in currentVersion.indices) {
            if (currentVersion[i].toInt() != versionToCompare[i].toInt()) return false
        }

        return true
    }

    @JvmStatic
    fun getCraftBukkitClass(name: String): ClassReflection<*> {
        return Reflection.getClass("org.bukkit.craftbukkit.$serverVersion.$name")!!
    }

    @JvmStatic
    fun getNMSPath(name: String): String {
        return "net.minecraft.server.$serverVersion.$name"
    }

    @JvmStatic
    fun getNMSClass(vararg names: String): ClassReflection<*> {
        return Reflection.getClass(*names.map { getNMSPath(it) }.toTypedArray())!!
    }

    @JvmStatic
    fun getMojangAuthClass(name: String): ClassReflection<*> {
        return Reflection.getClass("com.mojang.authlib.$name", "net.minecraft.util.com.mojang.authlib.$name")!!
    }

    @JvmStatic
    fun getNettyClass(name: String): ClassReflection<*> {
        return Reflection.getClass("io.netty.$name", "net.minecraft.util.io.netty.$name")!!
    }

    @JvmStatic
    @JvmName("get")
    fun World.nms(): CraftWorldNMS {
        return CraftWorldNMS(this, getCraftBukkitClass("CraftWorld").cast(this))
    }

    @JvmStatic
    @JvmName("get")
    fun Player.nms(): CraftPlayerNMS {
        return CraftPlayerNMS(this, getCraftBukkitClass("entity.CraftPlayer").cast(this))
    }

    @JvmStatic
    @JvmName("get")
    fun Server.nms(): ServerNMS {
        return ServerNMS(
            this,
            NMSClasses.Bukkit.SERVER
                .findField(NMSClasses.Minecraft.SERVER.get(), 0)!!
                .value(this)!!
        )
    }

    @JvmStatic
    @JvmName("get")
    fun ItemStack.nms(): Any {
        return NMSClasses.Bukkit.ITEM_STACK
            .method("asNMSCopy")!!
            .invokeSelf(null, this)!!
    }

    @JvmStatic
    fun toBukkitItemStack(nmsItem: Any): ItemStack {
        return NMSClasses.Bukkit.ITEM_STACK
            .method("asBukkitCopy")!!
            .invokeSelf(null, nmsItem)!! as ItemStack
    }

}