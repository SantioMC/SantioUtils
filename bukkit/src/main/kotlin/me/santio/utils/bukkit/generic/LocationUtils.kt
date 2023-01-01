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

fun Pair<Location, Location>.min(): Location {
    val minX = this.first.x.coerceAtMost(this.second.x)
    val minY = this.first.y.coerceAtMost(this.second.y)
    val minZ = this.first.z.coerceAtMost(this.second.z)
    return Location(this.first.world, minX, minY, minZ)
}

fun Pair<Location, Location>.max(): Location {
    val maxX = this.first.x.coerceAtLeast(this.second.x)
    val maxY = this.first.y.coerceAtLeast(this.second.y)
    val maxZ = this.first.z.coerceAtLeast(this.second.z)
    return Location(this.first.world, maxX, maxY, maxZ)
}

fun Pair<Location, Location>.locations(): List<Location> {
    val locs: MutableList<Location> = mutableListOf()

    for (x in this.min().blockX..this.max().blockX) {
        for (y in this.min().blockY..this.max().blockY) {
            for (z in this.min().blockZ..this.max().blockZ) {
                locs.add(Location(this.first.world, x.toDouble(), y.toDouble(), z.toDouble()))
            }
        }
    }

    return locs.sortedWith(compareBy({ it.y }, { it.x }, { it.z }))
}

infix fun Pair<Location, Location>.blocks(world: World?): List<Block> {
    return this.locations().map { loc ->
        (world ?: Bukkit.getWorlds()[0]).getBlockAt(loc)
    }.toList()
}

fun Block.frame(): ItemFrame? {
    return this.frames().firstOrNull()
}

fun Block.frames(): List<ItemFrame> {
    return this.world
        .getNearbyEntities(this.location, 1.0, 1.0, 1.0) {
            it is ItemFrame && it.block() == this
        }
        .map { it as ItemFrame }
}