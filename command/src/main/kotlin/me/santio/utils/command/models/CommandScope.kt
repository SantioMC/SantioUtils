package me.santio.utils.command.models

data class CommandScope(
    val clazz: Class<*>,
    val instance: Any,
    val parent: CommandScope?,
    val children: MutableList<CommandScope> = mutableListOf(),
    val subcommands: MutableList<SubCommand> = mutableListOf(),
)
