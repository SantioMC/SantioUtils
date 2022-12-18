package me.santio.utils.bukkit.inventory.menu

import me.santio.utils.bukkit.inventory.menu.events.MenuClickEvent
import me.santio.utils.bukkit.inventory.menu.menus.VirtualMenu
import org.bukkit.event.inventory.InventoryClickEvent
import java.util.function.Consumer

class MenuInteractivity {

    var defaultCancelled = true
    private var clickEvents = mutableMapOf<Int, Consumer<MenuClickEvent>>()

    fun assignEvent(slot: Int, event: Consumer<MenuClickEvent>) {
        clickEvents[slot] = event
    }

    fun removeEvent(slot: Int) {
        clickEvents.remove(slot)
    }

    fun handleClick(event: InventoryClickEvent, menu: VirtualMenu) {
        val menuEvent = MenuClickEvent(event, menu)

        val handler = clickEvents[menuEvent.slot] ?: if (defaultCancelled) Consumer {
            it.isCancelled = true
        } else Consumer { }

        handler.accept(menuEvent)
    }

}