package me.santio.utils.bukkit.command

import me.santio.utils.reflection.types.MethodReflection
import org.bukkit.entity.Player

data class SubCommandInfo(
    val method: MethodReflection,
    val name: String,
    val permission: String?,
    val variant: Variant,
    val target: CommandTarget = CommandTarget.BOTH,
    val cooldown: Double,
) {
    enum class CommandTarget {
        PLAYER,
        CONSOLE,
        BOTH,
        ;

        fun isAcceptable(sender: Any) = when (this) {
            PLAYER -> sender is Player
            CONSOLE -> sender !is Player
            BOTH -> true
        }
    }
}
