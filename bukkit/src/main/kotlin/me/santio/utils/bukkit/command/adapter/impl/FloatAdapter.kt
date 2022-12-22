package me.santio.utils.bukkit.command.adapter.impl

import me.santio.utils.bukkit.command.adapter.ArgumentAdapter

object FloatAdapter: ArgumentAdapter<Float>() {
    override val type: Class<Float> = Float::class.java
    override fun adapt(arg: String): Float? = arg.toFloatOrNull()
}