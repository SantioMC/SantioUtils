package me.santio.utils.command.adapter.impl

import me.santio.utils.command.adapter.ArgumentAdapter

object FloatAdapter: ArgumentAdapter<Float>() {
    override val type: Class<Float> = Float::class.java
    override fun isValid(arg: String) = arg.toFloatOrNull() != null
    override fun adapt(arg: String): Float? = arg.toFloatOrNull()
}