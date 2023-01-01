package me.santio.utils.color

@Suppress("unused")
data class HSV(
    val hue: Float,
    val saturation: Float,
    val value: Float
) {
    fun toColor(): Color = Color.fromHSV(hue, saturation, value)
}
