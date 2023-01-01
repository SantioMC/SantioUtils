package me.santio.utils.bukkit.map

import org.bukkit.entity.Player
import org.bukkit.map.*
import java.awt.Image

@Suppress("MemberVisibilityCanBePrivate", "unused")
class CustomRenderer : MapRenderer() {

    var canvas: MapCanvas? = null
    var map: MapView? = null

    val maxX = 128
    val maxZ = 128

    override fun render(map: MapView, canvas: MapCanvas, player: Player) {
        if (this.canvas == null) this.canvas = canvas
        if (this.map == null) this.map = map
    }

    fun rect(x1: Int, z1: Int, x2: Int, z2: Int, color: Byte) {
        for (x in x1 until x2) {
            for (z in z1 until z2) {
                canvas?.setPixel(x, z, color)
            }
        }
    }

    fun setPixel(size: Int, x: Int, z: Int, color: Byte) {
        val pixelX = x + 1
        val pixelZ = z + 1
        rect((pixelX - 1) * size, (pixelZ - 1) * size, pixelX * size, pixelZ * size, color)
    }

    @Suppress("DEPRECATION")
    fun border(color: Byte) {
        rect(0, 0, maxX, maxZ, color)
        rect(1, 1, maxX - 1, maxZ - 1, MapPalette.WHITE)
    }

    @Suppress("DEPRECATION")
    fun text(x: Int, y: Int, text: String) {
        canvas?.drawText(x, y, MinecraftFont.Font, text)
    }

    fun centerText(text: String) {
        val lines = text.split("\n")
        val lineCount = lines.size
        val lineHeight = 10
        val totalHeight = lineCount * lineHeight
        val y = (maxZ - totalHeight) / 2
        for (i in lines.indices) {
            val line = lines[i]
            val x = (maxX - line.length * 4) / 2
            text(x, y + i * lineHeight, line)
        }
    }

    @Suppress("DEPRECATION")
    fun clear() {
        fill(MapPalette.TRANSPARENT)
    }

    fun fill(color: Byte) {
        rect(0, 0, maxX, maxZ, color)
    }

    fun setImage(image: Image) {
        canvas?.drawImage(0, 0, image)
    }

}