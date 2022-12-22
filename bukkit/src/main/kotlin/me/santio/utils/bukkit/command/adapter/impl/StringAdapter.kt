package me.santio.utils.bukkit.command.adapter.impl

import me.santio.utils.bukkit.command.adapter.ArgumentAdapter

object StringAdapter : ArgumentAdapter<String>() {
    override val type: Class<String> = String::class.java
    override fun adapt(arg: String): String = arg
}