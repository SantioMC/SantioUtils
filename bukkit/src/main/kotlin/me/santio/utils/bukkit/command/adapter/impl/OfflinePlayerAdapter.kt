package me.santio.utils.bukkit.command.adapter.impl

import me.santio.utils.bukkit.command.adapter.ArgumentAdapter
import org.bukkit.Bukkit
import org.bukkit.OfflinePlayer
import org.bukkit.entity.Player

@Suppress("DEPRECATION")
object OfflinePlayerAdapter: ArgumentAdapter<OfflinePlayer>() {
    override val type: Class<OfflinePlayer> = OfflinePlayer::class.java
    override fun adapt(arg: String): OfflinePlayer = Bukkit.getOfflinePlayer(arg)
}