package me.santio.utils.bukkit.command.annotation

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class Command(
    /**
     * The name of the command.
     * Example: `/<name>`
     */
    val name: String = "",

    /**
     * Alternative names for the command.
     * Example: `/<name>` or `/<alias>`
     */
    val aliases: Array<String> = [],

    /**
     * The permission required to execute the command and its subcommands.
     * If the player doesn't have the permission, it will not show up in tab completion.
     *
     * Example: `bukkit.command.op`
     */
    val permission: String = "",

    /**
     * The description of the command.
     */
    val description: String = "",

    /**
     * The cooldown of the command. This is for every subcommand as well.
     * Example: `5` (5 seconds)
     */
    val cooldown: Double = 0.0,

    /**
     * The fallback label for the command, this is the key for the command namespace
     * Example: `/<owner>:help` -> `/essentials:help`
     */
    val owner: String = "command",
)
