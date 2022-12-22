package me.santio.utils.bukkit.command.adapter.impl

import me.santio.utils.bukkit.command.adapter.ArgumentAdapter
import org.bukkit.Bukkit
import org.bukkit.entity.Player

object PlayerAdapter: ArgumentAdapter<Player>() {
    override val type: Class<Player> = Player::class.java
    override fun adapt(arg: String): Player? = Bukkit.getPlayer(arg)
}