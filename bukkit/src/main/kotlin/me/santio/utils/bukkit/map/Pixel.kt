package me.santio.utils.bukkit.map

data class Pixel(
    val map: CustomMap,
    val coordinates: Pair<Int, Int>
) {

    fun set(color: Byte) {
        map.renderer.setPixel(1, coordinates.first, coordinates.second, color)
    }

    fun get(): Byte? = map.renderer.canvas?.getPixel(coordinates.first, coordinates.second)

}
