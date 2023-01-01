package me.santio.utils.bukkit.raytrace

import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.entity.EntityType
import org.bukkit.entity.Player
import org.bukkit.util.Vector
import java.util.concurrent.CompletableFuture

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
    fun test() {
        lateinit var player: Player

        player.raytrace(20) {
            it.block?.type == Material.STONE
        }
    }

}