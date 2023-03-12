package me.santio.utils.command.models

import me.santio.utils.command.annotations.Command
import java.lang.reflect.Method

data class CommandEvaluation(
    val scope: CommandScope,
    val rootAnnotation: Command,
    val name: String,
    val entry: Int,
)

data class SubCommand(
    val name: String,
    val path: String,
    val scope: CommandScope,
    val method: Method,
    val arguments: List<CommandArgument>,
) {
    override fun toString(): String {
        return "SubCommand(name='$name', path='$path', scope=${scope.clazz}, method=${method.name}, arguments=${arguments.map { it.type.simpleName }})"
    }
}