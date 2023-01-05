package me.santio.utils.bukkit.map

data class Pixel(
    val customMap: CustomMap,
    override val coordinates: Pair<Int, Int>
): RawPixel(customMap.view(), coordinates) {

    fun set(color: Byte) {
        customMap.renderer.setPixel(1, coordinates.first, coordinates.second, color)
    }

    fun get(): Byte? = customMap.renderer.canvas?.getPixel(coordinates.first, coordinates.second)

}
