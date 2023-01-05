package me.santio.utils.bukkit.map

import org.bukkit.map.MapView

@Suppress("unused")
open class RawPixel(
    open val map: MapView,
    open val coordinates: Pair<Int, Int>
)