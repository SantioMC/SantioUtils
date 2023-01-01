@file:Suppress("unused")

package me.santio.utils.color

data class RGB(
    val red: Int,
    val green: Int,
    val blue: Int
) {
    fun toColor(): Color = Color.fromRGB(red, green, blue)
}