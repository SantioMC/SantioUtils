package me.santio.utils.color

import me.santio.utils.kotlin.floorToInt
import kotlin.math.abs

@Suppress("unused")
class Color {

    var red: Int = 0
    var green: Int = 0
    var blue: Int = 0

    companion object {
        @JvmStatic
        fun fromRGB(red: Int, green: Int, blue: Int): Color {
            return Color().apply {
                this.red = red
                this.green = green
                this.blue = blue
            }
        }

        @JvmStatic
        fun fromHex(hex: String): Color {
            return Color().apply {
                this.red = hex.substring(0, 2).toInt(16)
                this.green = hex.substring(2, 4).toInt(16)
                this.blue = hex.substring(4, 6).toInt(16)
            }
        }

        @JvmStatic
        fun fromHSV(hue: Float, saturation: Float, value: Float): Color {
            val c = value * saturation
            val x = c * (1 - abs((hue / 60) % 2 - 1))
            val m = value - c

            return Color().apply {
                when (hue) {
                    in 0f..59f -> {
                        this.red = ((c + m) * 255).floorToInt()
                        this.green = ((x + m) * 255).floorToInt()
                        this.blue = ((m) * 255).floorToInt()
                    }
                    in 60f..119f -> {
                        this.red = ((x + m) * 255).floorToInt()
                        this.green = ((c + m) * 255).floorToInt()
                        this.blue = ((m) * 255).floorToInt()
                    }
                    in 120f..179f -> {
                        this.red = ((m) * 255).floorToInt()
                        this.green = ((c + m) * 255).floorToInt()
                        this.blue = ((x + m) * 255).floorToInt()
                    }
                    in 180f..239f -> {
                        this.red = ((m) * 255).floorToInt()
                        this.green = ((x + m) * 255).floorToInt()
                        this.blue = ((c + m) * 255).floorToInt()
                    }
                    in 240f..299f -> {
                        this.red = ((x + m) * 255).floorToInt()
                        this.green = ((m) * 255).floorToInt()
                        this.blue = ((c + m) * 255).floorToInt()
                    }
                    in 300f..359f -> {
                        this.red = ((c + m) * 255).floorToInt()
                        this.green = ((m) * 255).floorToInt()
                        this.blue = ((x + m) * 255).floorToInt()
                    }
                }
            }
        }

        @JvmStatic
        fun fromHue(hue: Int): Color {
            return fromHSV(hue.toFloat(), 1f, 1f)
        }
    }

    fun toRGB(): RGB = RGB(red, green, blue)

    fun toHex(): String {
        return "#${red.toString(16)}${green.toString(16)}${blue.toString(16)}"
    }

    fun toHSV(): HSV {
        val r = red / 255f
        val g = green / 255f
        val b = blue / 255f

        val cMax = maxOf(r, g, b)
        val cMin = minOf(r, g, b)
        val delta = cMax - cMin

        val hue = when {
            delta == 0f -> 0f
            cMax == r -> 60 * (((g - b) / delta) % 6)
            cMax == g -> 60 * (((b - r) / delta) + 2)
            cMax == b -> 60 * (((r - g) / delta) + 4)
            else -> 0f
        }

        val saturation = if (cMax == 0f) 0f else delta / cMax

        return HSV(hue, saturation, cMax)
    }

    fun toAWTColor(): java.awt.Color {
        return java.awt.Color(red, green, blue)
    }

    fun toJavaFXColor(): javafx.scene.paint.Color {
        return javafx.scene.paint.Color.rgb(red, green, blue)
    }

    fun toSwingColor(): java.awt.Color {
        return java.awt.Color(red, green, blue)
    }

}