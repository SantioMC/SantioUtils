package me.santio.utils.bukkit.plugin

import org.bukkit.plugin.PluginDescriptionFile
import java.nio.file.Path

data class PluginInfo(
    val path: Path,
    val name: String,
    val version: String,
    val description: PluginDescriptionFile
)
