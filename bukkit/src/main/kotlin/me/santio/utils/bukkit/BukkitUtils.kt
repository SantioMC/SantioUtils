package me.santio.utils.bukkit

import me.santio.utils.bukkit.features.BukkitFeature

object BukkitUtils {

    @JvmStatic
    val featuresEnabled: MutableSet<BukkitFeature> = mutableSetOf()

    @JvmStatic
    var currentMapId = 0

}