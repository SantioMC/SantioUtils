@file:JvmName("ChatUtils")
@file:Suppress("unused")

package me.santio.utils.bukkit.generic

import net.md_5.bungee.api.ChatColor
import net.md_5.bungee.api.chat.BaseComponent
import net.md_5.bungee.api.chat.TextComponent
import java.util.*
import java.util.regex.Matcher

private val hexPattern = Regex("&(#[a-fA-F\\d]{6})").toPattern()

fun String.colored(): String {
    val matcher: Matcher = hexPattern.matcher(ChatColor.translateAlternateColorCodes('&', this))
    val buffer = StringBuffer()

    while (matcher.find()) matcher.appendReplacement(buffer, ChatColor.of(matcher.group(1)).toString())

    return matcher.appendTail(buffer).toString()
}

fun String.strip(): String {
    return ChatColor.stripColor(this)
}

fun String.toComponent(): BaseComponent {
    return TextComponent(*TextComponent.fromLegacyText(this))
}

@Suppress("SpellCheckingInspection")
fun String.normalcase(): String {
    return this.split(" ", "-", "_").joinToString(" ") { s -> s.substring(0, 1).uppercase() + s.substring(1).lowercase() }
}