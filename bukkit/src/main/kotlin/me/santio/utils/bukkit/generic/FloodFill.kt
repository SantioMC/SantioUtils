@file:JvmName("FloodFill")
@file:Suppress("unused")

package me.santio.utils.bukkit.generic

import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.block.Block
import org.bukkit.block.BlockFace

private val defaultFaces = setOf(BlockFace.NORTH, BlockFace.EAST, BlockFace.SOUTH, BlockFace.WEST, BlockFace.UP, BlockFace.DOWN)

fun fill(location: Location, complete: MutableSet<Block>, types: Set<Material>, faces: Set<BlockFace>? = null, max: Int = 500): Set<Block> {
    if (complete.size >= max) return emptySet()
    if (types.contains(location.block.type)) complete.add(location.block)
    for (face in faces ?: defaultFaces) {
        val block = location.block.getRelative(face)
        if (types.contains(block.type) && !complete.contains(block)) complete.addAll(fill(block.location, complete, types, faces, max))
    }
    return if (complete.size >= max) emptySet() else complete
}

fun Block.floodfill(size: Int = 500, faces: Set<BlockFace>? = defaultFaces): Set<Block> {
    return fill(location, mutableSetOf(), setOf(type), faces, size)
}