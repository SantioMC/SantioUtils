package me.santio.utils.bukkit.command

data class CommandInfo(
    val name: String,
    val aliases: List<String>,
    val permission: String?,
    val description: String,
    val cooldown: Double,
    val owner: String,
)
