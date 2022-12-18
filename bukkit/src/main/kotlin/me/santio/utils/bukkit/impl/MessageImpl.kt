@file:JvmName("MessageUtils")

package me.santio.utils.bukkit.impl

import me.santio.utils.bukkit.generic.toComponent
import me.santio.utils.minecraft.message.Message
import net.md_5.bungee.api.chat.BaseComponent
import net.md_5.bungee.api.chat.ClickEvent
import net.md_5.bungee.api.chat.HoverEvent
import net.md_5.bungee.api.chat.hover.content.Text
import org.bukkit.entity.Player

fun Message.component(): BaseComponent {
    val component = this.text().toComponent()

    hover()?.let { component.hoverEvent = HoverEvent(HoverEvent.Action.SHOW_TEXT, Text(it)) }
    click()?.let {
        component.clickEvent = when (it.first) {
            Message.ClickAction.OPEN_URL -> ClickEvent(ClickEvent.Action.OPEN_URL, it.second)
            Message.ClickAction.SUGGEST_COMMAND -> ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, it.second)
            Message.ClickAction.RUN_COMMAND -> ClickEvent(ClickEvent.Action.RUN_COMMAND, it.second)
        }
    }

    children().forEach { component.addExtra(it.component()) }

    return component
}

fun Player.sendMessage(message: Message) {
    this.spigot().sendMessage(message.component())
}

fun Message.send(player: Player) {
    player.sendMessage(this)
}