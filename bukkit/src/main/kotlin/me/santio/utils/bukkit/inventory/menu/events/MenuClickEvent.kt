package me.santio.utils.bukkit.inventory.menu.events

import me.santio.utils.bukkit.inventory.menu.menus.VirtualMenu
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack

class MenuClickEvent(
    private val event: InventoryClickEvent,
    private val inventory: VirtualMenu
): InventoryClickEvent(
    event.view,
    event.slotType,
    event.slot,
    event.click,
    event.action
) {

    fun getVirtualInventory(): VirtualMenu {
        return inventory
    }

    @Suppress("DEPRECATION")
    @Deprecated("Deprecated in Java")
    override fun setCursor(stack: ItemStack?) {
        this.event.cursor = stack
    }

    override fun getCursor(): ItemStack? = this.event.cursor

    override fun setCurrentItem(stack: ItemStack?) {
        this.event.currentItem = stack
    }

    override fun getCurrentItem(): ItemStack? = this.event.currentItem

    override fun setCancelled(toCancel: Boolean) {
        this.event.isCancelled = toCancel
    }

    override fun isCancelled(): Boolean = this.event.isCancelled

    override fun setResult(newResult: Result) {
        this.event.result = newResult
    }

    override fun getResult(): Result = this.event.result

}