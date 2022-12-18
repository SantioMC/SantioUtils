@file:JvmName("EventUtils")
@file:Suppress("unused")

package me.santio.utils.bukkit.generic

import org.bukkit.event.block.Action

fun Action.isRightClick(): Boolean {
    return this == Action.RIGHT_CLICK_AIR || this == Action.RIGHT_CLICK_BLOCK
}

fun Action.isLeftClick(): Boolean {
    return this == Action.LEFT_CLICK_AIR || this == Action.LEFT_CLICK_BLOCK
}

fun Action.isClick(): Boolean {
    return this.isRightClick() || this.isLeftClick()
}

fun Action.isPhysical(): Boolean {
    return this == Action.PHYSICAL
}