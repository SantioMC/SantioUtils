package me.santio.utils.command.annotations

/**
 * Marks the given class as a command. It can also be used to provide
 * declared classes extra information.
 */
@Target(AnnotationTarget.CLASS, AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class Command(
    val name: String = "",
    val description: String = "",
    val aliases: Array<String> = [],
    val permission: String = "",
    val cooldown: Double = 0.0,
)
