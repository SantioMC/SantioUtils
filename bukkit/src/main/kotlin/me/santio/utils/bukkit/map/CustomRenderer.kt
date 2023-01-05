package me.santio.utils.bukkit.map

import org.bukkit.entity.Player
import org.bukkit.map.*
import java.awt.Image
import java.util.function.Consumer

@Suppress("MemberVisibilityCanBePrivate", "unused")
class CustomRenderer : MapRenderer() {

    private var onRenders: MutableList<Consumer<CustomRenderer>> = mutableListOf()
    var canvas: MapCanvas? = null
    var map: MapView? = null

    val maxX = 128
    val maxZ = 128

    override fun render(map: MapView, canvas: MapCanvas, player: Player) {
        if (this.canvas == null) this.canvas = canvas
        if (this.map == null) this.map = map

        onRenders.forEach { it.accept(this) }
        onRenders.clear()
    }

    fun onRender(callback: Consumer<CustomRenderer>) {
        onRenders.add(callback)
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

    @JvmOverloads
    fun side(side: CustomMap.Side, color: Byte, width: Int = 1) {
        when (side) {
            CustomMap.Side.TOP -> rect(0, 0, maxX, width, color)
            CustomMap.Side.BOTTOM -> rect(0, maxZ - width, maxX, maxZ, color)
            CustomMap.Side.LEFT -> rect(0, 0, width, maxZ, color)
            CustomMap.Side.RIGHT -> rect(maxX - width, 0, maxX, maxZ, color)
        }
    }

    @Suppress("DEPRECATION")
    @JvmOverloads
    fun border(color: Byte, width: Int = 1) {
        CustomMap.Side.values().forEach { side(it, color, width) }
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

    fun image(image: Image) {
        canvas?.drawImage(0, 0, image)
    }

}