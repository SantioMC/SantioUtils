package me.santio.utils.bukkit.map

import me.santio.utils.bukkit.generic.*
import org.bukkit.Location
import org.bukkit.block.BlockFace
import org.bukkit.entity.GlowItemFrame
import kotlin.math.abs

@Suppress("MemberVisibilityCanBePrivate", "unused")
class MultiMap {

    private val matrix: MutableList<MutableList<CustomMap>> = mutableListOf()

    fun generate(topLeft: Location, bottomRight: Location) {
        val blocks = topLeft to bottomRight blocks topLeft.world
        val direction: BlockFace = blocks.facing()

        blocks.forEach { block ->
            val x = if (direction == BlockFace.NORTH || direction == BlockFace.SOUTH) abs(block.x - topLeft.x) .toInt()
            else abs(block.z - topLeft.z).toInt()
            val y = abs(block.y - topLeft.y).toInt()

            val itemFrame = block.frame() ?: block.attachEntity(GlowItemFrame::class.java, direction)
            val map = CustomMap()
            itemFrame.setItem(map.item())
            matrix[x][y] = map
        }
    }

    fun getPixel(x: Int, y: Int): Pixel {
        val mapX = x / 128
        val mapY = y / 128
        val pixelX = x % 128
        val pixelY = y % 128

        return Pixel(matrix[mapX][mapY], pixelX to pixelY)
    }

    fun sphere(center: Pixel, radius: Int): List<Pixel> {
        val pixels: MutableList<Pixel> = mutableListOf()

        for (x in -radius..radius) {
            for (y in -radius..radius) {
                for (z in -radius..radius) {
                    if (x * x + y * y + z * z <= radius * radius) {
                        pixels.add(getPixel(center.coordinates.first + x, center.coordinates.second + y))
                    }
                }
            }
        }
        return pixels
    }

    fun rect(x: Int, y: Int, width: Int, height: Int): List<Pixel> {
        val pixels: MutableList<Pixel> = mutableListOf()

        for (pX in x..x + width) {
            for (pY in y..y + height) {
                pixels.add(getPixel(pX, pY))
            }
        }

        return pixels
    }

    fun number() {
        for ((index, map) in matrix.flatten().withIndex()) {
            map.renderer.border(MapUtils.randomColor())
            map.renderer.centerText("$index")
        }
    }

}