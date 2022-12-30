package me.santio.utils.bukkit.generic

import org.bukkit.map.MapPalette
import kotlin.math.roundToInt

@Suppress("unused", "DEPRECATION")
object MapUtils {
    fun randomColor(): Byte {
        return MapPalette.matchColor(
            (Math.random() * 255).roundToInt(), (Math.random() * 255).roundToInt(), (Math.random() * 255).roundToInt()
        )
    }

    fun default() = MapPalette.matchColor(0, 0, 0)
}