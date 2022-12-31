@file:JvmName("LocationUtils")
@file:Suppress("unused")

package me.santio.utils.bukkit.generic

import org.bukkit.Bukkit
import org.bukkit.Chunk
import org.bukkit.Location
import org.bukkit.World
import org.bukkit.block.Block
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

    var y = this.first.y
    while (y != this.second.y) {
        var x = this.first.x
        while (x != this.second.x) {
            var z = this.first.z
            while (z != this.second.z) {
                locs += Location(this.first.world, x, y, z)
                z += if (this.first.z < this.second.z) 1.0 else -1.0
            }
            x += if (this.first.x < this.second.x) 1.0 else -1.0
        }
        y += if (this.first.y < this.second.y) 1.0 else -1.0
    }

    return locs.sortedWith(compareBy({ it.y }, { it.x }, { it.z }))
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