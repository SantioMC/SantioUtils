package me.santio.utils.bukkit.command.adapter.impl

import me.santio.utils.bukkit.command.adapter.ArgumentAdapter

object DoubleAdapter: ArgumentAdapter<Double>() {
    override val type: Class<Double> = Double::class.java
    override fun adapt(arg: String): Double? = arg.toDoubleOrNull()
}