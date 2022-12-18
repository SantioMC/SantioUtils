package me.santio.utils.bukkit.inventory.menu.menus

import me.santio.utils.bukkit.BukkitUtils
import me.santio.utils.bukkit.features.BukkitFeature
import me.santio.utils.bukkit.features.FeatureNotEnabledException
import me.santio.utils.bukkit.inventory.menu.MenuInteractivity
import me.santio.utils.bukkit.inventory.menu.MenuSlots
import me.santio.utils.bukkit.inventory.menu.Slots
import me.santio.utils.bukkit.inventory.menu.events.MenuClickEvent
import org.bukkit.Bukkit
import org.bukkit.entity.HumanEntity
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType
import org.bukkit.event.inventory.InventoryAction
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.event.inventory.InventoryOpenEvent
import org.bukkit.event.inventory.InventoryType
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.InventoryView
import org.bukkit.inventory.ItemStack
import java.util.UUID
import java.util.function.Consumer

@Suppress("unused", "MemberVisibilityCanBePrivate")
open class VirtualMenu(rows: Int, name: String) {

    constructor(rows: Int): this(rows, "Menu")

    init {
        if (!BukkitUtils.featuresEnabled.contains(BukkitFeature.INVENTORY))
            throw FeatureNotEnabledException("The inventory feature is not enabled! You are not able to construct virtual menus!")

        if (rows < 1 || rows > 6) throw IllegalArgumentException("Rows must be between 1 and 6")
    }

    private var title = name
    private var size: Int = rows * 9
    private var inventory: Inventory = Bukkit.createInventory(null, size, title)
    private var interactivity = MenuInteractivity()
    private var slots = MenuSlots()
    private var views: MutableMap<UUID, InventoryView> = mutableMapOf()

    private fun generate() {
        inventory = Bukkit.createInventory(null, size, title)
        slots.apply(inventory)
    }

    fun title(name: String): VirtualMenu {
        title = name
        inventory.viewers.forEach { open(it) }
        return this
    }

    fun resize(rows: Int): VirtualMenu {
        size = rows * 9
        inventory.viewers.forEach { open(it) }
        return this
    }

    fun title(): String = title
    fun size(): Int = size

    fun open(vararg player: HumanEntity): VirtualMenu {
        generate()
        player.forEach {
            views[it.uniqueId] = it.openInventory(inventory)!!
        }
        return this
    }

    fun openedFromEvent(event: InventoryOpenEvent) {
        generate()
        views[event.player.uniqueId] = event.view
    }

    fun closedFromEvent(event: InventoryCloseEvent) {
        views.remove(event.player.uniqueId)
    }

    fun isOpen(player: Player): Boolean = player.openInventory.topInventory == inventory

    fun setSlot(slot: Int, item: ItemStack): VirtualMenu {
        slots.setSlot(slot, item)
        return refresh()
    }

    fun setSlot(slot: Int, item: ItemStack, event: Consumer<MenuClickEvent>): VirtualMenu {
        slots.setSlot(slot, item)
        interactivity.assignEvent(slot, event)
        return refresh()
    }

    fun setSlots(slot: Slots, item: ItemStack): VirtualMenu {
        slot.apply(this).forEach { setSlot(it, item) }
        return refresh()
    }

    fun setSlots(slot: Slots, item: ItemStack, event: Consumer<MenuClickEvent>): VirtualMenu {
        slot.apply(this).forEach { setSlot(it, item, event) }
        return refresh()
    }

    fun removeSlot(slot: Int): VirtualMenu {
        slots.removeSlot(slot)
        interactivity.removeEvent(slot)
        return refresh()
    }

    fun refresh(): VirtualMenu {
        inventory.viewers.forEach { open(it) }
        return this
    }

    fun getSlot(slot: Int): ItemStack? {
        return slots.getSlot(slot)
    }

    fun handleClick(event: InventoryClickEvent) {
        interactivity.handleClick(event, this)
    }

    @JvmOverloads
    fun simulateClick(player: Player, slot: Int, action: ClickType = ClickType.LEFT) {
        val view = views[player.uniqueId] ?: return

        interactivity.handleClick(InventoryClickEvent(
            view,
            InventoryType.SlotType.CONTAINER,
            slot,
            action,
            when(action) {
                ClickType.LEFT -> InventoryAction.PICKUP_ALL
                ClickType.RIGHT -> InventoryAction.PICKUP_HALF
                ClickType.MIDDLE -> InventoryAction.PICKUP_ONE
                ClickType.SHIFT_LEFT -> InventoryAction.MOVE_TO_OTHER_INVENTORY
                ClickType.SHIFT_RIGHT -> InventoryAction.MOVE_TO_OTHER_INVENTORY
                ClickType.NUMBER_KEY -> InventoryAction.HOTBAR_MOVE_AND_READD
                ClickType.DROP -> InventoryAction.DROP_ALL_SLOT
                ClickType.CONTROL_DROP -> InventoryAction.DROP_ALL_CURSOR
                ClickType.DOUBLE_CLICK -> InventoryAction.COLLECT_TO_CURSOR
                else -> InventoryAction.NOTHING
            }
        ), this)
    }

}