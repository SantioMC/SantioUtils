package me.santio.utils.command.adapter.impl

import me.santio.utils.command.adapter.ArgumentAdapter

object IntAdapter: ArgumentAdapter<Int>() {
    override val type: Class<Int> = Int::class.java
    override fun isValid(arg: String) = arg.toIntOrNull() != null
    override fun adapt(arg: String): Int? = arg.toIntOrNull()
}