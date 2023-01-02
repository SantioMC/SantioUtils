package me.santio.utils.bukkit.map

import me.santio.utils.bukkit.generic.*
import org.bukkit.Location
import org.bukkit.block.BlockFace
import org.bukkit.entity.GlowItemFrame
import java.awt.Image
import java.awt.image.BufferedImage
import java.net.URL
import java.util.function.Consumer
import javax.imageio.ImageIO
import kotlin.math.abs

@Suppress("MemberVisibilityCanBePrivate", "unused")
class MultiMap {

    private val maps = mutableListOf<PartMap>()

    @JvmOverloads
    fun generate(topLeft: Location, bottomRight: Location, direction: BlockFace, callback: Consumer<MultiMap>? = null): MultiMap {
        var locations = (topLeft to bottomRight).locations()

        locations = when(direction) {
            BlockFace.NORTH -> locations.sortedWith(compareBy({ it.y }, { it.x }, { it.z })).reversed()
            BlockFace.SOUTH -> locations.sortedWith(compareBy({ it.y }, { -it.x }, { -it.z })).reversed()
            BlockFace.EAST -> locations.sortedWith(compareBy({ it.y }, { it.z }, { it.x })).reversed()
            BlockFace.WEST -> locations.sortedWith(compareBy({ it.y }, { -it.z }, { -it.x })).reversed()
            else -> locations
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

        var waitingFor = maps.size
        maps.forEach { partMap ->
            partMap.map.renderer.onRender {
                waitingFor--
                if (waitingFor == 0) callback?.accept(this)
            }
        }

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

    fun sphere(x: Int, y: Int, radius: Int): List<Pixel> {
        val pixels: MutableList<Pixel> = mutableListOf()

        for (i in 0..radius) {
            for (j in 0..radius) {
                if (i * i + j * j <= radius * radius) {
                    pixels.add(getPixel(x + i, y + j)!!)
                    pixels.add(getPixel(x + i, y - j)!!)
                    pixels.add(getPixel(x - i, y + j)!!)
                    pixels.add(getPixel(x - i, y - j)!!)
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
            part.map.renderer.centerText("$index\n${part.x}:${part.y}")
        }

        return this
    }

    fun image(image: Image): MultiMap {
        val maps = maps() ?: return this

        // Scale the image to fit the size of the map
        val scaledWidth = (maps.maxBy { it.x }.x + 1) * 128
        val scaledHeight = (maps.maxBy { it.y }.y + 1) * 128
        val scaledImage = image.getScaledInstance(scaledWidth, scaledHeight, Image.SCALE_SMOOTH)

        // Split the image into 128x128 parts
        for (part in maps) {
            val img = BufferedImage(128, 128, BufferedImage.TYPE_INT_ARGB)
            val graphics = img.graphics
graphics.drawImage(scaledImage, 0, 0, 128, 128, part.x * 128, part.y * 128, (part.x + 1) * 128, (part.y + 1) * 128, null)
            graphics.dispose()
            part.map.renderer.image(img)
        }

        for ((index, part) in maps.withIndex()) {
            part.map.renderer.border(MapUtils.randomColor())
            part.map.renderer.centerText("$index\n${part.x}:${part.y}")
        }

        return this
    }

    fun image(url: URL): MultiMap {

        async {
            val req = url.openConnection()
            val image = req.getInputStream().use { ImageIO.read(it) }
            image(image)
        }

        return this
    }

}