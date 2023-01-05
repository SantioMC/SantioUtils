package me.santio.utils.bukkit.raytrace

import me.santio.utils.bukkit.generic.frame
import me.santio.utils.bukkit.map.RawPixel
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.block.BlockFace
import org.bukkit.entity.EntityType
import org.bukkit.inventory.meta.MapMeta
import org.bukkit.util.Vector
import java.util.concurrent.CompletableFuture
import kotlin.math.roundToInt

@Suppress("unused")
object Raytrace {

    @JvmStatic
    fun block(material: Material, loc: Location, direction: Vector, distance: Double = 20.0): CompletableFuture<VectorData?> {
        return Trace(loc.toVector(), direction, loc.world!!)
            .maxDistance(distance)
            .filter { it.block?.type == material }
            .trace()
    }

    @JvmStatic
    fun entity(loc: Location, direction: Vector, distance: Double = 20.0): CompletableFuture<VectorData?> {
        return Trace(loc.toVector(), direction, loc.world!!)
            .maxDistance(distance)
            .filter { it.entities.isNotEmpty() }
            .trace()
    }

    @JvmStatic
    fun entity(type: EntityType, loc: Location, direction: Vector, distance: Double = 20.0): CompletableFuture<VectorData?> {
        return Trace(loc.toVector(), direction, loc.world!!)
            .maxDistance(distance)
            .filter { it.entities.any { e -> e.type == type } }
            .trace()
    }

    @JvmStatic
    fun map(loc: Location, direction: Vector, distance: Double = 20.0): CompletableFuture<VectorData?> {
        return Trace(loc.toVector(), direction, loc.world!!)
            .maxDistance(distance)
            .filter {
                it.block != null && it.block.frame() != null
            }
            .trace()
    }

    @JvmStatic
    fun mapPixel(loc: Location, direction: Vector, distance: Double = 20.0): CompletableFuture<RawPixel?> {
        val future = CompletableFuture<RawPixel?>()
        Trace(loc.toVector(), direction, loc.world!!)
            .maxDistance(distance)
            .filter {
                val frame = it.block?.frame()
                it.block != null
                    && frame != null
                    && frame.item.type == Material.FILLED_MAP
                    && frame.item.itemMeta is MapMeta
            }
            .trace()
            .thenAccept { trace ->
                if (trace == null) {
                    future.complete(null)
                    return@thenAccept
                }

                val frame = trace.block!!.frame()!!
                val map = (frame.item.itemMeta as MapMeta).mapView

                val l = trace.location

                val y = ((l.y - l.blockY) * 128).roundToInt()
                val x = (frame.facing == BlockFace.NORTH || frame.facing == BlockFace.SOUTH).let {
                    if (it) ((l.x - l.blockX) * 128).roundToInt() else ((l.z - l.blockZ) * 128).roundToInt()
                }

                future.complete(RawPixel(map!!, x to y))
            }

        return future
    }

}