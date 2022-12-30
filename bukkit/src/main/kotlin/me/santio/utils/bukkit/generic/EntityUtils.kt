@file:JvmName("EntityUtils")

package me.santio.utils.bukkit.generic

import org.bukkit.block.Block
import org.bukkit.block.BlockFace
import org.bukkit.entity.Hanging

fun Hanging.block(): Block {
    return this.location.block.getRelative(this.attachedFace)
}

fun <T: Hanging>Block.attachEntity(entity: Class<out T>, face: BlockFace = BlockFace.NORTH): T {
    val e = this.world.spawn(this.location, entity)
    e.setFacingDirection(face, true)
    return e
}