package me.santio.utils.bukkit.command.adapter

abstract class ArgumentAdapter<T> {

    /**
     * The type of the argument.
     * Example: Player::class.java
     */
    abstract val type: Class<T>
    open val error: String = "Invalid argument: %s"

    /**
     * The function to convert the argument to the type.
     * Example: Bukkit.getPlayerExact(it)
     *
     * @param arg The argument to convert.
     * @return The converted argument. If null, an error message will be sent and the command execution will stop
     */
    abstract fun adapt(arg: String): T?

}