package me.santio.utils.bukkit.map

import me.santio.utils.bukkit.generic.*
import org.bukkit.Location
import org.bukkit.block.BlockFace
import org.bukkit.entity.GlowItemFrame
import java.util.function.Consumer
import kotlin.math.abs

@Suppress("MemberVisibilityCanBePrivate", "unused")
class MultiMap {

    val maps = mutableListOf<PartMap>()

    @JvmOverloads
    fun generate(topLeft: Location, bottomRight: Location, direction: BlockFace, callback: Consumer<MultiMap>? = null): MultiMap {
        val locations = (topLeft to bottomRight).locations()

        when(direction) {
            BlockFace.NORTH -> locations.sortedWith(compareBy({ -it.y }, { -it.x }, { it.z }))
            BlockFace.SOUTH -> locations.sortedWith(compareBy({ -it.y }, { it.x }, { it.z }))
            BlockFace.EAST -> locations.sortedWith(compareBy({ -it.y }, { it.z }, { it.x }))
            BlockFace.WEST -> locations.sortedWith(compareBy({ -it.y }, { -it.z }, { it.x }))
            else -> {/* do nothing */}
        }

        locations.blocks(topLeft.world).forEach { block ->
            val x = if (direction == BlockFace.NORTH || direction == BlockFace.SOUTH) abs(block.x - topLeft.x) .toInt()
            else abs(block.z - topLeft.z).toInt()
            val y = abs(block.y - topLeft.y).toInt()

            val itemFrame = block.frame() ?: block.attachEntity(GlowItemFrame::class.java, direction)
            val map = CustomMap()
            itemFrame.setItem(map.item())

            maps.add(PartMap(map, x, y))
        }

        delay({
            callback?.accept(this)
        }, 20)

        return this
    }

    fun maps(): List<PartMap>? {
        return if (maps.isEmpty()) null else maps
    }

    fun getPixel(x: Int, y: Int): Pixel? {
        val maps = maps() ?: return null

        val mapX = x / 128
        val mapY = y / 128
        val pixelX = x % 128
        val pixelY = y % 128

        val map = maps.firstOrNull { it.x == mapX && it.y == mapY } ?: return null
        return Pixel(map.map, pixelX to pixelY)
    }

    fun sphere(center: Pixel, radius: Int): List<Pixel> {
        val pixels: MutableList<Pixel> = mutableListOf()

        for (x in -radius..radius) {
            for (y in -radius..radius) {
                for (z in -radius..radius) {
                    if (x * x + y * y + z * z <= radius * radius) {
                        val pixel = getPixel(center.coordinates.first + x, center.coordinates.second + y) ?: continue
                        pixels.add(pixel)
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
                val pixel = getPixel(pX, pY) ?: continue
                pixels.add(pixel)
            }
        }

        return pixels
    }

    fun number(): MultiMap {
        val maps = maps() ?: return this

        for ((index, part) in maps.withIndex()) {
            part.map.renderer.border(MapUtils.randomColor())
            part.map.renderer.centerText("$index")
        }

        return this
    }

}