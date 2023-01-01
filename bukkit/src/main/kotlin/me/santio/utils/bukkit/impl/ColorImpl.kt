package me.santio.utils.bukkit.impl

import me.santio.utils.color.Color

fun Color.toBukkitColor(): org.bukkit.Color = org.bukkit.Color.fromRGB(red, green, blue)
fun Color.toSpigotColor(): net.md_5.bungee.api.ChatColor = net.md_5.bungee.api.ChatColor.of(java.awt.Color(red, green, blue))
fun Color.toSpigotText() = "&${this.toHex()}"