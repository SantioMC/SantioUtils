package me.santio.utils.bukkit.command.adapter.impl

import me.santio.utils.bukkit.command.adapter.ArgumentAdapter
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player

object MaterialAdapter: ArgumentAdapter<Material>() {
    override val type: Class<Material> = Material::class.java
    override fun adapt(arg: String): Material? = Material.matchMaterial(arg)
}