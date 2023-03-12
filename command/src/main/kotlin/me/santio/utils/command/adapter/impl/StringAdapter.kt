package me.santio.utils.command.adapter.impl

import me.santio.utils.command.adapter.ArgumentAdapter

object StringAdapter : ArgumentAdapter<String>() {
    override val type: Class<String> = String::class.java
    override fun isValid(arg: String) = true
    override fun adapt(arg: String): String = arg
}