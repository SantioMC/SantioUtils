package me.santio.utils.command.adapter.impl

import me.santio.utils.command.adapter.ArgumentAdapter

object DoubleAdapter: ArgumentAdapter<Double>() {
    override val type: Class<Double> = Double::class.java
    override fun isValid(arg: String) = arg.toDoubleOrNull() != null
    override fun adapt(arg: String): Double? = arg.toDoubleOrNull()
}