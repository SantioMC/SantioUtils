package me.santio.utils.command.models

data class CommandArgument(
    val name: String,
    val type: Class<*>,
    val optional: Boolean,
    val infinite: Boolean,
    val individual: Class<*>,
)
