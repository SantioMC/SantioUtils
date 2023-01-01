package me.santio.utils.bukkit.map

import me.santio.utils.bukkit.BukkitUtils
import me.santio.utils.bukkit.inventory.item.CustomItem
import org.bukkit.Bukkit
import org.bukkit.map.MapView

@Suppress("DEPRECATION", "MemberVisibilityCanBePrivate")
class CustomMap {

    private val map: MapView
    private val id = BukkitUtils.currentMapId++
    val renderer = CustomRenderer()

    init {
        map = Bukkit.getMap(id) ?: Bukkit.createMap(Bukkit.getWorlds()[0])
        map.renderers.clear()
        map.addRenderer(renderer)
    }

    fun item() = CustomItem.map(map)

}