package me.santio.utils.bukkit.raytrace

import org.bukkit.Location
import org.bukkit.block.Block
import org.bukkit.entity.Entity

data class VectorData(
    val entities: List<Entity>,
    val block: Block?,
    val location: Location
)
