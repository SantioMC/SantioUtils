package me.santio.utils.bukkit.command.annotation

import me.santio.utils.bukkit.command.Variant

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class SubCommand(
    val name: String = "",
    val permission: String = "",
    val variant: Variant = Variant.REGULAR,
    val cooldown: Double = 0.0,
)
