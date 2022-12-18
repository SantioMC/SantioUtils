package me.santio.utils.bukkit.inventory.menu

import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack

class MenuSlots {
    private var slots: MutableMap<Int, ItemStack> = mutableMapOf()

    fun setSlot(slot: Int, item: ItemStack) {
        slots[slot] = item
    }

    fun removeSlot(slot: Int) {
        slots.remove(slot)
    }

    fun getSlot(slot: Int): ItemStack? {
        return slots[slot]
    }

    fun apply(inventory: Inventory) {
        slots.forEach { (slot, item) ->
            inventory.setItem(slot, item)
        }
    }
}