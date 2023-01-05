@file:JvmName("ColorUtils")
@file:Suppress("unused")

package me.santio.utils.bukkit.impl

import me.santio.utils.color.Color
import org.bukkit.map.MapPalette

fun Color.toBukkitColor(): org.bukkit.Color = org.bukkit.Color.fromRGB(red, green, blue)
fun Color.toSpigotColor(): net.md_5.bungee.api.ChatColor = net.md_5.bungee.api.ChatColor.of(java.awt.Color(red, green, blue))
fun Color.toSpigotText() = "&${this.toHex()}"

@Suppress("DEPRECATION")
fun Color.toMapPalette() = MapPalette.matchColor(red, green, blue)