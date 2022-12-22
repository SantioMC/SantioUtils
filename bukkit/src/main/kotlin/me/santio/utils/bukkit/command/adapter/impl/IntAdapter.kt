package me.santio.utils.bukkit.command.adapter.impl

import me.santio.utils.bukkit.command.adapter.ArgumentAdapter

object IntAdapter: ArgumentAdapter<Int>() {
    override val type: Class<Int> = Int::class.java
    override fun adapt(arg: String): Int? = arg.toIntOrNull()
}