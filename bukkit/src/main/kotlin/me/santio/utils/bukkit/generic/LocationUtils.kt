@file:JvmName("LocationUtils")
@file:Suppress("unused")

package me.santio.utils.bukkit.generic

import me.santio.utils.kotlin.normalize
import org.bukkit.Bukkit
import org.bukkit.Chunk
import org.bukkit.Location
import org.bukkit.World
import org.bukkit.block.Block
import org.bukkit.block.BlockFace
import org.bukkit.entity.Entity
import org.bukkit.entity.ItemFrame
import java.util.function.Predicate

fun Location.getAsyncEntities(predicate: Predicate<Entity>? = null): Set<Entity> {
    return getChunks().map { chunk -> chunk.entities.filter { b -> predicate?.test(b) ?: true } }.flatten().toSet()
}

fun Location.getChunks(radius: Int = 2): Set<Chunk> {
    val chunks: MutableSet<Chunk> = mutableSetOf()

    chunks.add(this.chunk)
    chunks.add(this.clone().add(radius.toDouble(), 0.0, 0.0).chunk)
    chunks.add(this.clone().add(-radius.toDouble(), 0.0, 0.0).chunk)
    chunks.add(this.clone().add(0.0, 0.0, radius.toDouble()).chunk)
    chunks.add(this.clone().add(0.0, 0.0, -radius.toDouble()).chunk)

    return chunks
}

fun Location.horizontalDistance(location: Location): Double {
    val distX = this.x - location.x
    val distZ = this.z - location.z
    return (distX * distX) + (distZ * distZ)
}

fun Pair<Location, Location>.locations(): List<Location> {
    val locs: MutableList<Location> = mutableListOf()

    var x = this.first.x
    var y = this.first.y
    var z = this.first.z

    while (y != this.second.y) {
        while (x != this.second.x) {
            while (z != this.second.z) {
                locs.add(Location(this.first.world, x, y, z))
                z += (this.first.z - this.second.z).normalize()
            }
            x += (this.first.x - this.second.x).normalize()
        }
        y += (this.first.y - this.second.y).normalize()
    }

    return locs
}

infix fun Pair<Location, Location>.blocks(world: World?): List<Block> {
    return this.locations().map { loc ->
        (loc.world ?: Bukkit.getWorlds()[0]).getBlockAt(loc)
    }.toList()
}

fun Block.frame(): ItemFrame? {
    return this.world
        .getNearbyEntities(this.location, 1.0, 1.0, 1.0) {
            it is ItemFrame && it.block() == this
        }
        .firstOrNull() as ItemFrame?
}

fun Iterable<Block>.facing(): BlockFace {
    val topLeft = this.first()
    val bottomRight = this.last()

    return if (topLeft.x < bottomRight.x) {
        if (topLeft.z < bottomRight.z) {
            BlockFace.NORTH
        } else {
            BlockFace.EAST
        }
    } else {
        if (topLeft.z < bottomRight.z) {
            BlockFace.WEST
        } else {
            BlockFace.SOUTH
        }
    }
}